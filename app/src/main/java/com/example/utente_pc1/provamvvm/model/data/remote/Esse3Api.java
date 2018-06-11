package com.example.utente_pc1.provamvvm.model.data.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.utente_pc1.provamvvm.model.data.local.GroupSubj;
import com.example.utente_pc1.provamvvm.model.data.local.SingleSubj;
import com.example.utente_pc1.provamvvm.util.exceptions.ConnectionException;
import com.example.utente_pc1.provamvvm.util.exceptions.LoginException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Esse3Api {
    private static final String URLBASE =
            "https://www.studenti.ict.uniba.it/esse3/";
    private static final String URLLOGIN =
            "https://www.studenti.ict.uniba.it/esse3/auth/Logon.do";
    private static final String URLLOGOUT =
            "https://www.studenti.ict.uniba.it/esse3/Logout.do";
    private static final String URLLIBRETTO =
            "https://www.studenti.ict.uniba.it/esse3/auth/studente/Libretto/LibrettoHome.do";

    private DomRequester requester;
    private String domLibretto;
    private HashMap<String, String> subSubjs;


    public Esse3Api() {
        this.requester = new DomRequester();
        domLibretto = new String();
        subSubjs = new HashMap<>();
    }

    public void Login(String name, String password) throws ConnectionException, LoginException {
        this.requester.setAuthentication(name, password);
        this.requester.setUrl(URLLOGIN);
        this.requester.retriveDom();
    }

    public void Logout() throws ConnectionException {
        this.requester.setUrl(URLLOGOUT);
        this.requester.doSingleReq();
        this.requester.resetAuthentication();
        domLibretto = new String();
        subSubjs = new HashMap<>();
    }


    private void retriveDomLibretto() throws ConnectionException, LoginException {
        this.requester.setUrl(URLLIBRETTO);
        this.domLibretto = this.requester.retriveDom();
    }

    public List<Float> getAvrg() throws ConnectionException, LoginException {
        List<Float> avrgVotes = new ArrayList<Float>();

        if (domLibretto.isEmpty()) {
            this.retriveDomLibretto();
        }

        Document doc = Jsoup.parse(domLibretto);
        Elements elements = doc.select("td.tplMaster");
        for (Element e : elements) {
            if (!e.text().isEmpty()) {
                String num = e.text()
                        .split("/")[0]
                        .trim();
                avrgVotes.add(Float.valueOf(num));
            }
        }
        return avrgVotes;
    }

    public List<String> getSubjects() throws ConnectionException, LoginException {
        Log.d("CHEBALLE", this.subSubjs.toString());
        List<String> subjects = new ArrayList<String>();

        if (domLibretto.isEmpty()) {
            this.retriveDomLibretto();
        }

        Document doc = Jsoup.parse(domLibretto);
        Elements elements = doc.select("td.detail_table_middle");
        for (Element e : elements) {
            if (!e.text().isEmpty()) {
                this.subSubjs.put(e.text(), e.select("a").attr("href").trim());
                subjects.add(e.text());
            }
        }
        return subjects;
    }


    public HashMap<String, Float> getDetailSubj(String subj) throws ConnectionException, LoginException {
        HashMap<String, Float> blockSubjHours = new HashMap<>();
        int div;
        int counter = 0;
        String key = new String();
        Float value = new Float(0);
        Document doc;
        Elements elements;

        if (subSubjs.isEmpty()) {
            this.getSubjects();
        }
        this.requester.setUrl(URLBASE + this.subSubjs.get(subj));
        String html = this.requester.retriveDom();

        /*
        int chunk = 1000;
        for (int i = 0; i < html.length(); i += chunk) {
            Log.d("HTML", html.substring(i, Math.min(html.length(), i + chunk)));
        }
        */
        doc = Jsoup.parse(html);
        elements = doc.select("th.detail_table");
        div = elements.size();
        //counter = 0;
        elements = doc.select("td.detail_table");

        for (int i = 0; i < elements.size() - (div - 1); i += div) {
            blockSubjHours.put(
                    elements.get(i)
                            .text()
                            .trim(),
                    Float.valueOf(elements.get(i + div - 1)
                            .text()
                            .trim())
            );
        }
        /*
        for (Element e : elements) {
            counter = counter % div;
            if (counter == 0) {
                key = e.text()
                        .split("-")[0]
                        .trim();
            } else if (counter == div - 1) {
                value = Float.valueOf(e.
                        text()
                        .trim());
                blockSubjHours.put(key, value);
            }
            counter++;
        }*/
        return blockSubjHours;
    }

    public Float getTotalBlockHours(String subj) throws ConnectionException, LoginException {
        HashMap<String, Float> res = this.getDetailSubj(subj);
        Float totalHours = new Float(0);
        for (Float hrs : res.values()) {
            totalHours += hrs;
        }
        return totalHours;
    }


    public LiveData<List<GroupSubj>> getBlocks() throws ConnectionException, LoginException {
        MutableLiveData<List<GroupSubj>> listLiveData = new MutableLiveData<List<GroupSubj>>();
        List<GroupSubj> blocks = new ArrayList<>();

        List<String> blockname = this.getSubjects();

        for (String sub : blockname) {
            Float hours = this.getTotalBlockHours(sub);
            blocks.add(new GroupSubj(sub, hours));
        }
        listLiveData.setValue(blocks);
        return listLiveData;
    }

    public List<SingleSubj> getSubjs() throws ConnectionException, LoginException {
        List<SingleSubj> subjects = new ArrayList<>();
        HashMap<String, Float> tmpsubj;

        List<String> blockname = this.getSubjects();

        for (String sub : blockname) {
            tmpsubj = this.getDetailSubj(sub);
            for (String subjName : tmpsubj.keySet()) {
                subjects.add(new SingleSubj(subjName, sub, tmpsubj.get(subjName)));
            }
        }
        return subjects;
    }
}
