//package com.lyl.web.lucene;
//
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.TokenStream;
//import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
//
//import java.io.IOException;
//import java.io.StringReader;
//
//public class StringAnalysis {
//
//    public static String getStringAnalysed(Analyzer analyzer, String str) {
//        StringReader reader = new StringReader(str);
//        TokenStream toStream = analyzer.tokenStream(str, reader);
//        StringBuilder stringBuilder = new StringBuilder();
//        String result = null;
//        try {
//            toStream.reset();// 清空流
//            CharTermAttribute teAttribute = toStream.getAttribute(CharTermAttribute.class);
//            while (toStream.incrementToken()) {
//                stringBuilder.append(teAttribute.toString());
//            }
//            if (stringBuilder.length() > 0 && stringBuilder.length() <= 80) {
//                result = stringBuilder.toString();
//            } else if (stringBuilder.length() > 80) {
//                result = stringBuilder.substring(0, 79);
//                result += "...";
//            }
//            else {
//                return null;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            analyzer.close();
//        }
//        return result;
//    }
//}
