package cn.geektown.helloworld.resources;


import cn.geektown.helloworld.api.Recognition;
import com.google.common.base.Optional;
import com.codahale.metrics.annotation.Timed;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Path("/recognition")
@Produces(MediaType.APPLICATION_JSON)
public class NLPRecognitionResource {

    @GET
    @Timed
    public List<Recognition> doRecognition(@QueryParam("corpus") Optional<String> corpus) {
        Segment segment = HanLP.newSegment().enableNameRecognize(true);
        List<Term> termList = segment.seg(corpus.or(""));
        List<Recognition> recognitions = new ArrayList<>() ;

        for (Term term : termList) {
            recognitions.add(new Recognition(term.word, term.nature.name() ));
        }
        return recognitions;
    }
}