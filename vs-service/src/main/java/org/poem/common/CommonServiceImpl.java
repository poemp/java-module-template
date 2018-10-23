package org.poem.common;

import org.springframework.stereotype.Service;

/**
 * 测试
 * @author poem
 */
@Service
public class CommonServiceImpl implements CommonService {


    /**
     * 测试框架
     * @return
     */
    @Override
    public CommonVO common() {
        CommonVO commonVO = new CommonVO();
        commonVO.setId(1L);
        commonVO.setName("test");
        return commonVO;
    }
}
