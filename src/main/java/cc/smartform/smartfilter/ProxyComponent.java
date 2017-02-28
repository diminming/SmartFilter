package cc.smartform.smartfilter;

import java.util.Map;

/**
 * Created by Matt Di on 2017/2/21.
 */
public interface ProxyComponent {

    void __setTarget__(Object target);
    void __setBlacklist__(Map<String, String[]> blacklists);
    Map<String, String[]> __getBlacklist__();

}
