package com.computer.server;

import org.junit.jupiter.api.Test;

public class OtherTest {
    @Test
    public void StringTest() {
        String Testcase = "100,000 123,456";
        String[] result = Testcase.split(" ");
        for(int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }
}
