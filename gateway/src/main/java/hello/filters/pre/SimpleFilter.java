package hello.filters.pre;

import javax.servlet.http.HttpServletRequest;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.ZuulFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleFilter extends ZuulFilter {

  private static Logger log = LoggerFactory.getLogger(SimpleFilter.class);

  @Override
  public String filterType() {
    // 过滤器的类型，有pre/route/post/error
    return "pre";
  }

  @Override
  public int filterOrder() {
    // 过滤器的优先级，越大越靠后执行
    return 1;
  }

  @Override
  public boolean shouldFilter() {
    // 是否需要过滤
    return true;
  }

  @Override
  public Object run() {
    // 过滤器所做的行动
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();

    log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

    return null;
  }

}
