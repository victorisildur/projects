package me.isildur.tomato2.me.isildur.tomato2.data;

/**
 * Created by isi on 16/1/25.
 */
public class Test {
    private static Test ourInstance = new Test();

    public static Test getInstance() {
        return ourInstance;
    }

    private Test() {
    }
}
