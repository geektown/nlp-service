package cn.geektown.helloworld.resources;


import com.google.common.base.Optional;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/recognition")
@Produces(MediaType.APPLICATION_JSON)
public class NLPRecognitionResource {

    @GET
    @Timed
    public Saying doRecognition(@QueryParam("corpus") Optional<String> corpus) {
        final String value = String.format(template, corpus.or(defaultName));
        return new Saying(counter.incrementAndGet(), value);
    }
}