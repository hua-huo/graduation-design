package com.example.topic.controller;

import com.example.topic.MetaDbUtils.MetaDbTemplate;
import com.example.topic.MetaDbUtils.ResultMsg;
import com.example.topic.config.CurrentUser;
import com.example.topic.model.Replyinfo;
import com.example.topic.model.User;
import com.example.topic.service.InvitationService;
import com.example.topic.utils.NumberTool;
import com.example.topic.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019\5\11 0011.
 */
@RestController
public class ContentController {
    @Autowired
    private InvitationService invitationService;
    @RequestMapping("/frontend/content/query")
    public Map queryDepartment(@RequestBody Map param) throws ParseException {
        int objid = NumberTool.transitionInteger(param, "objid", 0);
        MetaDbTemplate metaDbTemplate = invitationService.qureyContent(objid);
        invitationService.setReadNum(objid);
        List<Map> rlist = invitationService.qureyReplyinfo(objid);
        List<Map> ilist = metaDbTemplate.getResult();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //在数据返回之前对数据加工
        Map result = new HashMap<>();
        String title = "";
        String orgid ="";
        int edition = 1;
        for(Map map :ilist){
            title = StringUtils.transitionString(map,"title","");
            orgid = StringUtils.transitionString(map,"orgid","");
            edition = NumberTool.transitionInteger(map,"edition");
            String create_at = StringUtils.transitionString(map, "create_at", "");
            String update_at = StringUtils.transitionString(map,"update_at","");
            map.put("create_at",sdf.format(sdf.parse(create_at)));
            map.put("update_at",sdf.format(sdf.parse(update_at)));
            map.put("author",true);
        }
        for(Map map :rlist){
            String create_at = StringUtils.transitionString(map, "create_at", "");
            String update_at = StringUtils.transitionString(map,"update_at","");
            map.put("create_at",sdf.format(sdf.parse(create_at)));
            map.put("update_at",sdf.format(sdf.parse(update_at)));
            String rorgid =StringUtils.transitionString(map,"orgid","");
            if(orgid.equals(rorgid)){
                map.put("author",true);
            }
        }
        List<Map> list = invitationService.getEnumList("FORUM", "EDITION");
        String lasttitle = "";
        for(Map map :list){
            int value = NumberTool.transitionInteger(map, "value", 1);
            if(value==edition){
                lasttitle = StringUtils.transitionString(map,"label","");
                break;
            }
        }
        ilist.addAll(rlist);
        result.put("list", ilist);
        result.put("title",title);
        param.put("lasttitle",lasttitle);
        param.put("edition",edition);
        result.put("param",param);
        return result;
    }
    @RequestMapping("/frontend/Replyinfo/save")
    public ResultMsg replyinfoSave(@RequestBody Map param,HttpServletRequest request){
        ResultMsg reusltMsg = new ResultMsg();
       try {
           //这里的User是登陆时放入session的
           User user = CurrentUser.getUser();
           if(user !=null){
               String content = StringUtils.transitionString(param, "content", "");
               int articleid = NumberTool.transitionInteger(param, "articleid", 0);
               Replyinfo replyinfo = new Replyinfo();
               replyinfo.setContent(content);
               replyinfo.setOrgid(user.getOrgid());
               replyinfo.setArticleid(articleid);
               invitationService.save(replyinfo);
               invitationService.setReplyNum(articleid);
               reusltMsg.setMessage("SUCCESS");
               return reusltMsg;
           }else{
               reusltMsg.setMessage("ERROR");
               return reusltMsg;
           }

       }catch (Exception e){
           reusltMsg.setMessage("ERROR");
           e.printStackTrace();
       }
     return reusltMsg;
    }
    @RequestMapping("/frontend/Replyinfo/delete")
    public ResultMsg delete(@RequestBody Map param){
        ResultMsg msg = new ResultMsg();
        try{
            int id = NumberTool.transitionInteger(param,"id",0);
            invitationService.deleteReplyinfo(id);
            msg.setMessage("SUCCESS");
        }catch (Exception e){
            e.printStackTrace();
            msg.setMessage("ERROR");
        }
    return msg;
    }
}
