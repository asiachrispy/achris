//package com.dajie.crawler.zhihu.demo;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.charset.Charset;
//import java.util.Map;
//import java.util.Vector;
//
//
//
//public class HttpRequestUtil {
//    private String defaultContentEncoding;
//
//    public HttpRequestUtil() {
//        this.defaultContentEncoding = Charset.defaultCharset().name();
//    }
//
//    public HttpRequestUtil(String encoding) {
//        this.defaultContentEncoding = encoding;
//    }
//
//    /**
//     * 发送GET请求
//     *
//     * @param urlString
//     *            URL地址
//     * @return 响应对象
//     * @throws IOException
//     */
//    public HttpResponsUtil sendGet(String urlString) throws IOException {
//        return this.send(urlString, "GET", null, null);
//    }
//
//    /**
//     * 发送GET请求
//     *
//     * @param urlString
//     *            URL地址
//     * @param params
//     *            参数集合
//     * @return 响应对象
//     * @throws IOException
//     */
//    public HttpResponsUtil sendGet(String urlString, Map<String, String> params)
//            throws IOException {
//        return this.send(urlString, "GET", params, null);
//    }
//
//    /**
//     * 发送GET请求
//     *
//     * @param urlString
//     *            URL地址
//     * @param params
//     *            参数集合
//     * @param propertys
//     *            请求属性
//     * @return 响应对象
//     * @throws IOException
//     */
//    public HttpResponsUtil sendGet(String urlString, Map<String, String> params,
//            Map<String, String> propertys) throws IOException {
//        return this.send(urlString, "GET", params, propertys);
//    }
//
//    /**
//     * 发送POST请求
//     *
//     * @param urlString
//     *            URL地址
//     * @return 响应对象
//     * @throws IOException
//     */
//    public HttpResponsUtil sendPost(String urlString) throws IOException {
//        return this.send(urlString, "POST", null, null);
//    }
//
//    /**
//     * 发送POST请求
//     *
//     * @param urlString
//     *            URL地址
//     * @param params
//     *            参数集合
//     * @return 响应对象
//     * @throws IOException
//     */
//    public HttpResponsUtil sendPost(String urlString, Map<String, String> params)
//            throws IOException {
//        return this.send(urlString, "POST", params, null);
//    }
//
//    /**
//     * 发送POST请求
//     *
//     * @param urlString
//     *            URL地址
//     * @param params
//     *            参数集合
//     * @param propertys
//     *            请求属性
//     * @return 响应对象
//     * @throws IOException
//     */
//    public HttpResponsUtil sendPost(String urlString, Map<String, String> params,
//            Map<String, String> propertys) throws IOException {
//        return this.send(urlString, "POST", params, propertys);
//    }
//
//    /**
//     * 发送HTTP请求
//     *
//     * @param urlString
//     * @return 响映对象
//     * @throws IOException
//     */
//    private HttpResponsUtil send(String urlString, String method,
//            Map<String, String> parameters, Map<String, String> propertys)
//            throws IOException {
//        HttpURLConnection urlConnection = null;
//
//        if (method.equalsIgnoreCase("GET") && parameters != null) {
//            StringBuffer param = new StringBuffer();
//            int i = 0;
//            for (String key : parameters.keySet()) {
//                if (i == 0)
//                    param.append("?");
//                else
//                    param.append("&");
//                param.append(key).append("=").append(parameters.get(key));
//                i++;
//            }
//            urlString += param;
//        }
//        URL url = new URL(urlString);
//        urlConnection = (HttpURLConnection) url.openConnection();
//
//        urlConnection.setRequestMethod(method);
//        urlConnection.setDoOutput(true);
//        urlConnection.setDoInput(true);
//        urlConnection.setUseCaches(false);
//
//        if (propertys != null)
//            for (String key : propertys.keySet()) {
//                urlConnection.addRequestProperty(key, propertys.get(key));
//            }
//
//        if (method.equalsIgnoreCase("POST") && parameters != null) {
//            StringBuffer param = new StringBuffer();
//            for (String key : parameters.keySet()) {
//                param.append("&");
//                param.append(key).append("=").append(parameters.get(key));
//            }
//            urlConnection.getOutputStream().write(param.toString().getBytes());
//            urlConnection.getOutputStream().flush();
//            urlConnection.getOutputStream().close();
//        }
//
//        return this.makeContent(urlString, urlConnection);
//    }
//
//    /**
//     * 得到响应对象
//     *
//     * @param urlConnection
//     * @return 响应对象
//     * @throws IOException
//     */
//    private HttpResponsUtil makeContent(String urlString,
//            HttpURLConnection urlConnection) throws IOException {
//        HttpResponsUtil httpResponser = new HttpResponsUtil();
//        try {
//            InputStream in = urlConnection.getInputStream();
//            //这里如果不指定InputStreamReader的编码，当中文字符超过两个时就会出现乱码（utf8情况下）
//            BufferedReader bufferedReader = new BufferedReader(
//                    new InputStreamReader(in, this.defaultContentEncoding));
//            httpResponser.contentCollection = new Vector<String>();
//            StringBuffer temp = new StringBuffer();
//            String line = bufferedReader.readLine();
//            while (line != null) {
//                httpResponser.contentCollection.add(line);
//                temp.append(line);  //.append("\r\n")
//                line = bufferedReader.readLine();
//            }
//            bufferedReader.close();
//
//            String ecod = urlConnection.getContentEncoding();
//            if (ecod == null){
//                ecod = this.defaultContentEncoding;
//                httpResponser.content = temp.toString();
//                /*test code.*/
//                System.out.println("ecod is" + ecod);
//                //ecod isUTF-8
//                System.out.println("temp = " + temp.toString());
//                //temp = {"tags":[{"r_id":3553740735078269,"tag":"再测试"},{"r_id":3555331928010955,"tag":"再测试"},{"r_id":3555332033148401,"tag":"再测试,测试好多个字符串"}],"rids":"3555332033148401,3555331928010955,3553740735078269,3553509628288136,3552801450193593,3550638895838037,3550607615227068,3550334377352366,3550333965816322,3550315184067715,3550136490112256,3550098295918936,3550094449884887,3550094122996559,3550092986220398,3550090717120421,3550090532251655,3550089362475695,3549897523877864,3549756045751486,3549719639064459,3549564726035910,3549379539997223,3549377979585685,3549373109656624","idArr":["3555332033148401","3555331928010955","3553740735078269","3553509628288136","3552801450193593","3550638895838037","3550607615227068","3550334377352366","3550333965816322","3550315184067715","3550136490112256","3550098295918936","3550094449884887","3550094122996559","3550092986220398","3550090717120421","3550090532251655","3550089362475695","3549897523877864","3549756045751486","3549719639064459","3549564726035910","3549379539997223","3549377979585685","3549373109656624"],"uId":1916364215,"type":3}
//                System.out.println("temp after encode: "+new String(temp.toString().getBytes(), ecod));
//                //temp after encode: {"tags":[{"r_id":3553740735078269,"tag":"?????"},{"r_id":3555331928010955,"tag":"?????"},{"r_id":3555332033148401,"tag":"?????,??????????"}],"rids":"3555332033148401,3555331928010955,3553740735078269,3553509628288136,3552801450193593,3550638895838037,3550607615227068,3550334377352366,3550333965816322,3550315184067715,3550136490112256,3550098295918936,3550094449884887,3550094122996559,3550092986220398,3550090717120421,3550090532251655,3550089362475695,3549897523877864,3549756045751486,3549719639064459,3549564726035910,3549379539997223,3549377979585685,3549373109656624","idArr":["3555332033148401","3555331928010955","3553740735078269","3553509628288136","3552801450193593","3550638895838037","3550607615227068","3550334377352366","3550333965816322","3550315184067715","3550136490112256","3550098295918936","3550094449884887","3550094122996559","3550092986220398","3550090717120421","3550090532251655","3550089362475695","3549897523877864","3549756045751486","3549719639064459","3549564726035910","3549379539997223","3549377979585685","3549373109656624"],"uId":1916364215,"type":3}
//                String str = "{\"tags\":[{\"r_id\":3553740735078269,\"tag\":\"再测试\"},{\"r_id\":3555331928010955,\"tag\":\"再测试\"},{\"r_id\":3555332033148401,\"tag\":\"再测试,测试好多个字符串\"}],\"rids\":\"3555332033148401,3555331928010955,3553740735078269,3553509628288136,3552801450193593,3550638895838037,3550607615227068,3550334377352366,3550333965816322,3550315184067715,3550136490112256,3550098295918936,3550094449884887,3550094122996559,3550092986220398,3550090717120421,3550090532251655,3550089362475695,3549897523877864,3549756045751486,3549719639064459,3549564726035910,3549379539997223,3549377979585685,3549373109656624\",\"idArr\":[\"3555332033148401\",\"3555331928010955\",\"3553740735078269\",\"3553509628288136\",\"3552801450193593\",\"3550638895838037\",\"3550607615227068\",\"3550334377352366\",\"3550333965816322\",\"3550315184067715\",\"3550136490112256\",\"3550098295918936\",\"3550094449884887\",\"3550094122996559\",\"3550092986220398\",\"3550090717120421\",\"3550090532251655\",\"3550089362475695\",\"3549897523877864\",\"3549756045751486\",\"3549719639064459\",\"3549564726035910\",\"3549379539997223\",\"3549377979585685\",\"3549373109656624\"],\"uId\":1916364215,\"type\":3}";
//
//                System.out.println("compare two string:" + temp.toString().equals(str));
//                //compare two string:true
//                StringBuffer sb = new StringBuffer();
//                sb.append(str);
//                String s = new String(sb.toString().getBytes(), "UTF-8");
//                System.out.println("s test:" + s);
//                //s test:{"tags":[{"r_id":3553740735078269,"tag":"?????"},{"r_id":3555331928010955,"tag":"?????"},{"r_id":3555332033148401,"tag":"?????,??????????"}],"rids":"3555332033148401,3555331928010955,3553740735078269,3553509628288136,3552801450193593,3550638895838037,3550607615227068,3550334377352366,3550333965816322,3550315184067715,3550136490112256,3550098295918936,3550094449884887,3550094122996559,3550092986220398,3550090717120421,3550090532251655,3550089362475695,3549897523877864,3549756045751486,3549719639064459,3549564726035910,3549379539997223,3549377979585685,3549373109656624","idArr":["3555332033148401","3555331928010955","3553740735078269","3553509628288136","3552801450193593","3550638895838037","3550607615227068","3550334377352366","3550333965816322","3550315184067715","3550136490112256","3550098295918936","3550094449884887","3550094122996559","3550092986220398","3550090717120421","3550090532251655","3550089362475695","3549897523877864","3549756045751486","3549719639064459","3549564726035910","3549379539997223","3549377979585685","3549373109656624"],"uId":1916364215,"type":3}
//                /*test code.*/
//
//            } else{
//                httpResponser.content = new String(temp.toString().getBytes(), ecod);
//            }
//
//            httpResponser.urlString = urlString;
//
//            httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();
//            httpResponser.file = urlConnection.getURL().getFile();
//            httpResponser.host = urlConnection.getURL().getHost();
//            httpResponser.path = urlConnection.getURL().getPath();
//            httpResponser.port = urlConnection.getURL().getPort();
//            httpResponser.protocol = urlConnection.getURL().getProtocol();
//            httpResponser.query = urlConnection.getURL().getQuery();
//            httpResponser.ref = urlConnection.getURL().getRef();
//            httpResponser.userInfo = urlConnection.getURL().getUserInfo();
//
//            httpResponser.contentEncoding = ecod;
//            httpResponser.code = urlConnection.getResponseCode();
//            httpResponser.message = urlConnection.getResponseMessage();
//            httpResponser.contentType = urlConnection.getContentType();
//            httpResponser.method = urlConnection.getRequestMethod();
//            httpResponser.connectTimeout = urlConnection.getConnectTimeout();
//            httpResponser.readTimeout = urlConnection.getReadTimeout();
//
//            return httpResponser;
//        } catch (IOException e) {
//            throw e;
//        } finally {
//            if (urlConnection != null)
//                urlConnection.disconnect();
//        }
//    }
//
//    /**
//     * 默认的响应字符集
//     */
//    public String getDefaultContentEncoding() {
//        return this.defaultContentEncoding;
//    }
//
//    /**
//     * 设置默认的响应字符集
//     */
//    public void setDefaultContentEncoding(String defaultContentEncoding) {
//        this.defaultContentEncoding = defaultContentEncoding;
//    }
//
//    public static void main(String[] args) throws Exception {
//        String str = "{\"tags\":[{\"r_id\":3553740735078269,\"tag\":\"再测试\"},{\"r_id\":3555331928010955,\"tag\":\"再测试\"},{\"r_id\":3555332033148401,\"tag\":\"再测试,测试好多个字符串\"}],\"rids\":\"3555332033148401,3555331928010955,3553740735078269,3553509628288136,3552801450193593,3550638895838037,3550607615227068,3550334377352366,3550333965816322,3550315184067715,3550136490112256,3550098295918936,3550094449884887,3550094122996559,3550092986220398,3550090717120421,3550090532251655,3550089362475695,3549897523877864,3549756045751486,3549719639064459,3549564726035910,3549379539997223,3549377979585685,3549373109656624\",\"idArr\":[\"3555332033148401\",\"3555331928010955\",\"3553740735078269\",\"3553509628288136\",\"3552801450193593\",\"3550638895838037\",\"3550607615227068\",\"3550334377352366\",\"3550333965816322\",\"3550315184067715\",\"3550136490112256\",\"3550098295918936\",\"3550094449884887\",\"3550094122996559\",\"3550092986220398\",\"3550090717120421\",\"3550090532251655\",\"3550089362475695\",\"3549897523877864\",\"3549756045751486\",\"3549719639064459\",\"3549564726035910\",\"3549379539997223\",\"3549377979585685\",\"3549373109656624\"],\"uId\":1916364215,\"type\":3}";
//        StringBuffer sb = new StringBuffer();
//        sb.append(str);
//        String s = new String(sb.toString().getBytes(), "UTF-8");
//        System.out.println("s test:" + s);
//        //s test:{"tags":[{"r_id":3553740735078269,"tag":"再测试"},{"r_id":3555331928010955,"tag":"再测试"},{"r_id":3555332033148401,"tag":"再测试,测试好多个字符串"}],"rids":"3555332033148401,3555331928010955,3553740735078269,3553509628288136,3552801450193593,3550638895838037,3550607615227068,3550334377352366,3550333965816322,3550315184067715,3550136490112256,3550098295918936,3550094449884887,3550094122996559,3550092986220398,3550090717120421,3550090532251655,3550089362475695,3549897523877864,3549756045751486,3549719639064459,3549564726035910,3549379539997223,3549377979585685,3549373109656624","idArr":["3555332033148401","3555331928010955","3553740735078269","3553509628288136","3552801450193593","3550638895838037","3550607615227068","3550334377352366","3550333965816322","3550315184067715","3550136490112256","3550098295918936","3550094449884887","3550094122996559","3550092986220398","3550090717120421","3550090532251655","3550089362475695","3549897523877864","3549756045751486","3549719639064459","3549564726035910","3549379539997223","3549377979585685","3549373109656624"],"uId":1916364215,"type":3}
//    }
//}
