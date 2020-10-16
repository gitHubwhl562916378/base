package com.osmagic.base;

import com.koala.osmagic.face_feature.cpp.FaceFeature;
import com.koala.osmagic.face_feature.cpp.FaceFeatureDTO;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
public class FaceFeatureController {
    private FaceFeature faceFeature =  new FaceFeature();
    private Object feature_mtx = "1";
//    private final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(6);

    @RequestMapping("/face-feature")
    public FaceFeatureDTO OnFile(MultipartFile file){
        long tie  = System.currentTimeMillis();
        FaceFeatureDTO result = null;

        try {
            byte[] bytes = file.getBytes();
//            Future<Mat> future = fixedThreadPool.submit(()->Imgcodecs.imdecode(src, Imgcodecs.IMREAD_COLOR));

//            Mat image = null;
//            try {
//                image = future.get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }

            long last = System.currentTimeMillis();
            synchronized (feature_mtx)
            {
                last = System.currentTimeMillis();
                result = faceFeature.feature(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("feature use " + (System.currentTimeMillis() - tie) + " millics");
        return result;
    }

    @PostMapping("/multi-files")
    public  String multi_handle(@RequestParam(value = "face") MultipartFile f, @RequestParam(value = "body") MultipartFile b) throws IOException {
        File face = new File("face.jpg");
        face.createNewFile();
        File body = new File("body.jpg");
        body.createNewFile();
        f.transferTo(face);
        b.transferTo(body);
        return "ok";
    }

    @PostMapping("/form")
    public String form(MultipartFile file)
    {
        return "file";
    }

    @PostMapping("/qps")
    public String qps()
    {
        return "qppps";
    }

    @PostMapping("/binary") //https://www.xjyili.cn/3415.html http中body参数解析
    public String binary(@RequestBody BinaryBody boyd)
    {

        return  "";
    }

//    @RequestMapping(value = "/test", consumes = { //consumes 只处理json格式的body
//            "application/json"
//    }, headers = {
//            "content-type=application/JSON"
//    }, produces = {
//            "application/json" //只处理请求头包含Accept: application/json的请求，返回格式是json
//    })
//    public String OnTest()
//    {
//        return "test";
//    }

    /**
     * Post使用@RequestBody注解将Json格式的参数自动绑定到Entity类
     * @param order
     * @return
     */
//    @PostMapping("/order/check")
//    public String checkOrder(@RequestBody Order order){
//        String result = "id:"+order.getId()+",name:"+order.getName()+",price:"+order.getPrice();
//        return result;
//    }

    /**
     * Post使用@RequestParam获取请求体中非Json格式的数据
     * @param amount
     * @param discount
     * @return
     */
//    @PostMapping("/order/checkmore")
//    public String checkMore(@RequestParam(value = "amount")Integer amount, @RequestParam(value = "discount")float discount){
//        String result = "amount:"+amount+",discount:"+discount;
//        return result;
//    }
}