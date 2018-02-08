package org.mascheraveneziana.zitan.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    // テスト用データの内1つを返します
    // レスポンスデータの例：{ id: 1, name: "成田" }
    @RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
    public Test getTest(@PathVariable int id) {
        // テスト用データ（4つ）
        Test[] testData = {
                new Test(1, "成田"),
                new Test(2, "羽田"),
                new Test(3, "金田"),
                new Test(4, "山田")
        };

        int index = id % 4;
        Test test = testData[index];
        return test;
    }

    // テストデータをリストで返します
    // レスポンスデータの例：[ { id: 1, name: "成田" }, { id: 2, name: "羽田" } ]
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public List<Test> getTestList() {

        Test test1 = new Test();
        test1.setId(1);
        test1.setName("成田");

        Test test2 = new Test();
        test2.setId(2);
        test2.setName("羽田");

        List<Test> testList = new ArrayList<>();
        testList.add(test1);
        testList.add(test2);

        return testList;
    }

}
