package com.osmagic.base;

import com.koala.osmagic.face_feature.cpp.FaceFeature;
import com.koala.osmagic.face_feature.cpp.FaceFeatureDTO;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FaceFeatureController {
    private FaceFeature faceFeature =  new FaceFeature();
    private Object feature_mtx = "1";

    @RequestMapping("/face-feature")
    public FaceFeatureDTO OnFile(MultipartFile file){
        long tie  = System.currentTimeMillis();
        FaceFeatureDTO result = null;

        try {
            byte[] bytes = file.getBytes();
            Mat src = new Mat(1, bytes.length, CvType.CV_8UC1);
            src.put(0, 0, bytes);

            long last = System.currentTimeMillis();
            Mat image = Imgcodecs.imdecode(src, Imgcodecs.IMREAD_COLOR);
            System.out.println("decode mat use " + (System.currentTimeMillis() - last) + " millics");

            synchronized (feature_mtx)
            {
                result = faceFeature.feature(image.getNativeObjAddr());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("feature use " + (System.currentTimeMillis() - tie) + " millics");
        return result;
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

    @PostMapping("/binary")
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