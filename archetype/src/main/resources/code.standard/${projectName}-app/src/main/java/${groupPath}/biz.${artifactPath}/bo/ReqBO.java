package ${groupPath}.biz.${artifactPath}.bo;

import ${groupPath}.biz.${artifactPath}.bo.base.IdBO;
import com.smalld.common.page.PageBO;

import java.io.Serializable;

public class ReqBO extends IdBO implements Serializable {

    private static final long serialVersionUID = -7402578259481764279L;

    private PageBO page;
}
