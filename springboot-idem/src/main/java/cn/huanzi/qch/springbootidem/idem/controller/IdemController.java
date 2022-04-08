package cn.huanzi.qch.springbootidem.idem.controller;


import cn.huanzi.qch.springbootidem.idem.entity.Idem;
import cn.huanzi.qch.springbootidem.idem.service.IdemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/idem/")
public class IdemController {

    private final IdemService idemService;

    public IdemController(IdemService idemService) {
        this.idemService = idemService;
    }

    @RequestMapping("/insert")
    public Object insert(Idem idem) {
        return  idemService.insert(idem);
    }

    @RequestMapping("/delete")
    public Object delete(Idem idem) {
        return  idemService.delete(idem);
    }

    @RequestMapping("/update")
    public Object update(Idem idem) {
        return  idemService.update(idem);
    }

    @RequestMapping("/select")
    public Object select(Idem idem) {
        return  idemService.select(idem);
    }
}
