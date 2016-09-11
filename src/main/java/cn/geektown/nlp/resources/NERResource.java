package cn.geektown.nlp.resources;


import cn.geektown.nlp.api.Recognition;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/hanlp/ner")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class NERResource {

    @GET
    @Timed
    public String doRecognition(@QueryParam("corpus") Optional<String> corpus) {
        // Segment segment = HanLP.newSegment().enableNameRecognize(true);
        List<Term> termList = NLPTokenizer.segment(corpus.or(""));
        List<Recognition> recognitions = new ArrayList<>();

        for (Term term : termList) {
            recognitions.add(new Recognition(term.word, term.nature.name()));
        }
        return null;

    }
}