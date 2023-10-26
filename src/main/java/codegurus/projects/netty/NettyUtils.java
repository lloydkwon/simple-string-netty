package codegurus.projects.netty;

public class NettyUtils {
    private NettyUtils(){}

    public static String appendNewLine(String msg) {
        return msg + '\n';
//        return msg + "\r\n";
//        return msg + Delimiters.lineDelimiter();
    }

}
