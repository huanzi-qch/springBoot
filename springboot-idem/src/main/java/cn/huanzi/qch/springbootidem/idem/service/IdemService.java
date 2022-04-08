package cn.huanzi.qch.springbootidem.idem.service;

import cn.huanzi.qch.springbootidem.idem.entity.Idem;

public interface IdemService {
    Object insert(Idem idem);

    Object delete(Idem idem);

    Object update(Idem idem);

    Object select(Idem idem);
}
