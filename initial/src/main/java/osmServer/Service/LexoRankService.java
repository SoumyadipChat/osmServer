package osmServer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import osmServer.Model.Playlist;
import osmServer.Model.User;
import osmServer.Model.VideoItem;
import osmServer.Repository.VideoItemRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class LexoRankService {

    @Autowired
    UserService userService;

    @Autowired
    PlaylistService playlistService;

    @Autowired
    MusicService musicService;

    @Autowired
    VideoItemRepository videoItemRepository;

    public void initialRanking(){
        List<User> userList=userService.getAllUsers();
       userList.stream()
               .forEach(this::rebalanceAllPlaylist);
    }

    public void rebalanceAllPlaylist(User user){
        List<Playlist> playlist=playlistService.getAllPlaylists(user.getUsername());
        rebalance("",user.getUsername());
        playlist.stream()
                .forEach(pl->rebalance(pl.getPlaylist(),user.getUsername()));
    }

    public Long rebalance(String plnm, String usernm){
        List<VideoItem> listToBalance=musicService.getAllItems(usernm).stream()
                .sorted()
                .filter(item->(StringUtils.isEmpty(plnm) && StringUtils.isEmpty(item.getPlaylist()))
                        || (!StringUtils.isEmpty(item.getPlaylist()) && !StringUtils.isEmpty(plnm) && plnm.equals(item.getPlaylist())))
                .collect(Collectors.toList());

        String[] currVal={"cc"};
        Consumer<VideoItem> setLexoRank=item->{
            currVal[0]=setValNext(item.getId(),currVal[0]);
        };

        listToBalance.stream()
                .forEach(setLexoRank);

        return (long)1;

    }

    public String setValNext(Long itemId,String currentVal){
        Optional<VideoItem> newItem=videoItemRepository.findById(itemId);
        if(!newItem.isPresent()){
            return currentVal;
        }
        VideoItem item=newItem.get();
        String prevVal=item.getLexoRank();
        char bucket=(StringUtils.isEmpty(prevVal) || prevVal.charAt(0)=='2')?'0':(char)(prevVal.charAt(0)+1);
        int currValInt=(currentVal.charAt(0)-'a')*26+(currentVal.length()>1?(currentVal.charAt(1)-'a'):0);
        currValInt+=8;
        int firstLetter=currValInt/26;
        int secondLetter=currValInt%26;
        String newVal=String.valueOf((char)(firstLetter+'a'))+String.valueOf((char)(secondLetter+'a'));
        String newLexoRank=String.valueOf(bucket)+"|"+newVal;
        item.setLexoRank(newLexoRank);


        videoItemRepository.save(item);


        return newVal;

    }

    public String setValBetween(Long itemId,String prev, String next){
        Optional<VideoItem> newItem=videoItemRepository.findById(itemId);
        if(!newItem.isPresent()){
            return "-1";
        }
        VideoItem item=newItem.get();
        String newLexoRank="";
        if(("first").equals(prev) && ("last").equals(next)){
            newLexoRank="0|cc";
        }
        else if(("first").equals(prev)){
            prev=next.substring(0,2)+"a";
        }
        else if (("last").equals(next)){
            next=prev.substring(0,2)+"zzzzz";
        }
        String firstStr=prev.substring(2);
        String netStr=next.substring(2);
        if(next.charAt(0)!=prev.charAt(0)){
            newLexoRank=prev+"n";
        }
        else{
            newLexoRank=prev.substring(0,2)+inBetween(firstStr,netStr);
        }

        item.setLexoRank(newLexoRank);
        videoItemRepository.save(item);
        if(newLexoRank.length()>7 || (newLexoRank.equals(prev+'a')) || (next.equals(newLexoRank+'a'))){
            return newLexoRank+"@rebalance";
        }
        else{
            return newLexoRank;
        }
    }


    public String inBetween(String prev,String next){
        if(prev.length()>0 && next.length()>0 && prev.charAt(0)==next.charAt(0)){
            return prev.charAt(0)+inBetween(prev.substring(1),next.substring(1));
        }
        int prevNum,nextNum;
        prevNum=prev.equals("")?0:prev.charAt(0)-'a'+1;
        nextNum=next.equals("")?0:next.charAt(0)-'a'+1;
        int diff=nextNum-prevNum;
        if(diff==1){
            int pre,nex;
            pre=prev.length()<=1?0:prev.charAt(1)-'a'+1;
            nex=next.length()<=1?27:next.charAt(1)-'a'+28;
            if(next.length()==1){
                return prev+"n";
            }
            else{
                int avg=(pre+nex)/2;
                if(avg==27){
                    return String.valueOf(next.charAt(0));
                }
                String result=avg<=26?
                        prev.charAt(0)+String.valueOf((char)('a'+avg-1)):
                        next.charAt(0)+String.valueOf((char)('a'+avg-28));
                return result;
            }
        }
        else{
            int avg=(prevNum+nextNum)/2;
            return String.valueOf((char)('a'+avg-1));
        }

    }

}
