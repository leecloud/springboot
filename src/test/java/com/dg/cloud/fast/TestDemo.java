package com.dg.cloud.fast;

import com.dg.cloud.fast.modules.sys.dao.SysDepDao;
import com.dg.cloud.fast.modules.sys.entity.SysDepEntity;
import com.dg.cloud.fast.modules.sys.service.impl.SysDepServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDemo {

    @Resource
    private SysDepDao sysDepDao;


    @Test
    public void queryAll(){
        List<SysDepEntity> sysDepEntities = sysDepDao.selectList(null);
        System.out.println(sysDepEntities.size());
    }
}
