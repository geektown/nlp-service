package cn.geektown.nlp.resources;


import cn.geektown.nlp.api.Recognition;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.py.Pinyin;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/hanlp/pinyinconverter")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class PinyinConverterResource {

    @GET
    @Timed
    public String doRecognition(@QueryParam("corpus") Optional<String> corpus) {
        List<Pinyin> pinyinList = HanLP.convertToPinyinList(corpus.or(""));

        StringBuffer pinyinConcat = new StringBuffer("");
        for (Pinyin pinyin : pinyinList) {
            pinyinConcat.append(pinyin.getPinyinWithoutTone()).append(" ");
        }
        return pinyinConcat.toString();

    }


}