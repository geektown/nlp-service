package cn.geektown.nlp.resources;


import cn.geektown.nlp.api.Recognition;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/hanlp/keyword")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class KeywordResource {

    @GET
    @Timed
    public List<Recognition> doRecognition(@QueryParam("corpus") Optional<String> corpus) {
        List<String> keywords = HanLP.extractKeyword(corpus.or(""), 5);
        List<Recognition> recognitions = new ArrayList<>();
        List<Term> termList = NLPTokenizer.segment(corpus.or(""));
        Map<String, Nature> map = new HashMap<>();
        for (Term term : termList) {
            map.put(term.word, term.nature);
        }
        recognitions.addAll(keywords.stream().map(word -> new Recognition(word, map.get(word).name())).collect(Collectors.toList()));
        return recognitions;
    }
}