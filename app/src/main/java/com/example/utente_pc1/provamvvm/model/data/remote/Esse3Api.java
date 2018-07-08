package com.example.utente_pc1.provamvvm.model.data.remote;

import com.example.utente_pc1.provamvvm.model.data.local.GroupSubj;
import com.example.utente_pc1.provamvvm.model.data.local.SingleSubj;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        domLibretto = "";
        subSubjs = new HashMap<>();
    }

    public void Login(String name, String password) throws Exception {
        this.requester.setAuthentication(name, password);
        this.requester.setUrl(URLLOGIN);
        this.requester.retriveDom();
    }

    public void Logout() throws Exception {
        this.requester.setUrl(URLLOGOUT);
        this.requester.doSingleReq();
        this.requester.resetAuthentication();
        domLibretto = "";
        subSubjs = new HashMap<>();
    }


    private void retriveDomLibretto() throws Exception{
        this.requester.setUrl(URLLIBRETTO);
        this.domLibretto = this.requester.retriveDom();
    }

    public List<Float> getAvrg() throws Exception {
        List<Float> avrgVotes = new ArrayList<>();

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

    public List<String> getSubjects() throws Exception {
        List<String> subjects = new ArrayList<>();

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


    public Map<String, Float> getDetailSubj(String subj) throws Exception {
        HashMap<String, Float> blockSubjHours = new HashMap<>();
        int div;
        Document doc;
        Elements elements;

        if (subSubjs.isEmpty()) {
            this.getSubjects();
        }
        this.requester.setUrl(URLBASE + this.subSubjs.get(subj));
        String html = this.requester.retriveDom();

        doc = Jsoup.parse(html);
        elements = doc.select("th.detail_table");
        div = elements.size();
        elements = doc.select("td.detail_table");

        for (int i = 0; i < elements.size() - (div - 1); i += div) {
            blockSubjHours.put(
                    elements.get(i)
                            .text()
                            .split("-")[0]
                            .trim(),
                    Float.valueOf(elements.get(i + div - 1)
                            .text()
                            .trim())
            );
        }
        return blockSubjHours;
    }

    public Float getTotalBlockHours(String subj) throws Exception {
        Map<String, Float> res = this.getDetailSubj(subj);
        Float totalHours = 0f;
        for (Float hrs : res.values()) {
            totalHours += hrs;
        }
        return totalHours;
    }


    public List<GroupSubj> getBlocks() throws Exception {
        List<GroupSubj> blocks = new ArrayList<>();

        List<String> blockname = this.getSubjects();

        for (String sub : blockname) {
            Float hours = this.getTotalBlockHours(sub);
            blocks.add(new GroupSubj(sub, hours));
        }
        return blocks;
    }

    public List<SingleSubj> getSubjs() throws Exception {
        List<SingleSubj> subjects = new ArrayList<>();
        Map<String, Float> tmpsubj;

        List<String> blockname = this.getSubjects();

        for (String sub : blockname) {
            tmpsubj = this.getDetailSubj(sub);
            for (Map.Entry<String, Float> pair : tmpsubj.entrySet()) {
                subjects.add(new SingleSubj(pair.getKey(), sub, pair.getValue()));
            }
        }
        return subjects;
    }
}
