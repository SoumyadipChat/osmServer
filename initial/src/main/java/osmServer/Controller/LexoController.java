package osmServer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import osmServer.Model.LexoRankRequest;
import osmServer.Model.VideoItem;
import osmServer.Service.LexoRankService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/lexo")
public class LexoController {

    @Autowired
    LexoRankService lexoRankService;

    @RequestMapping("/rebalanceAll")
    public int rebalanceAll(){
        lexoRankService.initialRanking();
        return 1;
    }


    @RequestMapping("/rebalance/{user}/{pl}")
    public Long rebalancePl(@PathVariable("user") String usernm, @PathVariable("pl") String pl){
        return lexoRankService.rebalance(pl,usernm);
    }

    @RequestMapping(value = "/setLexorank", method= RequestMethod.POST)
    public String setLexoRank(@RequestBody LexoRankRequest request){
        return lexoRankService.setValBetween(request.getId(),request.getPrev(),request.getNext());
    }
}
