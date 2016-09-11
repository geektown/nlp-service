package cn.geektown.nlp.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by david on 2016/6/9.
 */
public class Recognition {



        private String word;


        private String tag;

        public Recognition() {
            // Jackson deserialization
        }

        public Recognition(String word, String nature) {
            this.word = word;
            this.tag = nature;
        }

        @JsonProperty
        public String getWord() {
            return word;
        }

        @JsonProperty
        public String getTag() {
            return tag;
        }

}
