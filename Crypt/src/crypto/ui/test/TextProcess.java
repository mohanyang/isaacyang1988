package crypto.ui.test;

import java.io.IOException;

public class TextProcess {

    private static final int terminalLen = 60;

    private float percent = 0.0f;

    private float oldPercent = percent;

    private String desc = "Running:";

    private String processTxt = "=";

    private int preLen = 0;

    private final static byte[] bytes = new byte[] { 92, 124, 47 };

    private int bi = 0;

    private boolean stop = false;

    public static void main(String[] args) throws IOException {
        TextProcess test = new TextProcess();
        test.run();
        float per = 0.0f;
        for (int i = 0; i < 100; i++) {
            per += 0.01;
            test.setPercent(per);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
        }
        test.stop();
    }

    public void stop() {
        this.stop = true;
    }

    public void run() {
        Thread run = new Thread() {
            public void run() {
                while (!stop) {
                    try {
                        TextProcess.this.print();
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }

                }

            }
        };

        run.start();
    }

    public void print() throws IOException {
        for (; preLen > 0; preLen--) {
            System.out.write(8);
        }
        System.out.print(this.desc);
        preLen = this.desc.length();
        int processLen = (int) ((terminalLen - preLen) * this.percent);
        for (int i = 0; i < processLen; i++) {
            System.out.print(this.processTxt);
            preLen++;
            System.out.flush();
        }
        for (int i = terminalLen - preLen; i > 0; i--) {
            System.out.print(' ');
            preLen++;
        }
        System.out.write(bytes[bi]);
        bi = ++bi % 3;
        preLen++;
        String txtPer = String.valueOf(Math.round(this.percent * 100)) + "%";
        System.out.print(txtPer);
        preLen += txtPer.length();
    }

    public static byte[] getBytes() {
        return bytes;
    }

    public TextProcess(String desc, String processTxt) {
        super();
        this.desc = desc;
        this.processTxt = processTxt;
    }

    public TextProcess() {
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getProcessTxt() {
        return processTxt;
    }

    public void setProcessTxt(String processTxt) {
        this.processTxt = processTxt;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }
}

