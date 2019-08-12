package com.example.topic.controller;

import com.example.topic.MetaDbUtils.ITableBatch;
import com.example.topic.MetaDbUtils.MetaDbTemplate;
import com.example.topic.MetaDbUtils.ResultMsg;
import com.example.topic.config.CurrentUser;
import com.example.topic.model.Invitation;
import com.example.topic.model.User;
import com.example.topic.service.InvitationService;
import com.example.topic.MetaDbUtils.Result;
import com.example.topic.utils.NumberTool;
import com.example.topic.utils.StringUtils;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019\5\7 0007.
 */
@RestController
public class InvitationController implements ITableBatch {
    @Autowired
    private InvitationService invitationService;
    @RequestMapping("/frontend/Invitation/query")
    public Result queryDepartment(@RequestBody Result result) {
        MetaDbTemplate metaDbTemplate = invitationService.qureyInvitation(result.getParmap());
        return result.getResult(metaDbTemplate,this);//返回页面列表的数据
    }

    @RequestMapping("/frontend/Invitation/queryByOrgid")
    public ResultMsg queryByOrgid() {
        ResultMsg msg = new ResultMsg();
        List<Map> publish= invitationService.publishFindByOrgid().getResult();
        List<Map> reply= invitationService.replyFindByOrgid().getResult();
        List<Map> ulist = invitationService.getEnumList("FORUM", "EDITION");
        Map umap = new HashMap<>();
        for(Map map:ulist){
            int value = NumberTool.transitionInteger(map,"value");
            String label = StringUtils.transitionString(map, "label", "");
            umap.put(value,label);
        }
        for(Map map :publish){
            int  edition = NumberTool.transitionInteger(map, "edition");
            map.put("lasttitle",umap.get(edition));
            map.put("create_at",StringUtils.toDate(map,"create_at","yyyy-MM-dd HH:mm:ss"));
        }
        for(Map map :reply){
            int  edition = NumberTool.transitionInteger(map,"edition");
            map.put("lasttitle",umap.get(edition));
            map.put("create_at",StringUtils.toDate(map,"create_at","yyyy-MM-dd HH:mm:ss"));
        }
        msg.setMessage("SUCCESS");
        Map map= new HashMap<>();
        map.put("publish",publish);
        map.put("reply",reply);
        msg.setData(map);
        return msg;
    }
    @Override
    public List<Map> tableBatch(List<Map> list,Map param) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Map> elist= invitationService.getEnum("FORUM","ARTICLE");
        Map<String, String> enummap = new HashMap<>();
        if (elist != null && elist.size() > 0) {
            for (Map map1 : elist) {
                String value = StringUtils.transitionString(map1, "value", "");
                String name = StringUtils.transitionString(map1, "name", "");
                enummap.put(value, name);
            }
        }
        //在数据返回之前对数据加工
        for(Map map :list){
            Map map1= invitationService.getEndPelyd(NumberTool.transitionInteger(map,"objid",0));
            String replyname = StringUtils.transitionString(map1, "name", "");
            String replydate = StringUtils.transitionString(map1,"create_at","");
            String create_at = StringUtils.transitionString(map,"create_at","");
            String update_at = StringUtils.transitionString(map,"update_at","");
            map.put("create_at",sdf.format(sdf.parse(create_at)));
            map.put("update_at",sdf.format(sdf.parse(update_at)));
            String type = StringUtils.transitionString(map,"type","");
            map.put("type",enummap.get(type));
            map.put("replyname",replyname);
            if(!"".equals(replydate)){
                map.put("replydate",replydate.substring(0,replydate.indexOf(".")));
            }

        }
        return list;
    }

    @RequestMapping("/frontend/Invitation/queryenummap")
    public ResultMsg queryEnumMap(@RequestBody Map param) {
        ResultMsg msg = new ResultMsg();
        int edition = NumberTool.transitionInteger(param, "edition", 1);
        String title ="";
        List<Map> list = invitationService.getEnumList("FORUM", "EDITION");
        for(Map map :list){
            int value = NumberTool.transitionInteger(map,"value",1);
            if(value==edition){
                title = StringUtils.transitionString(map,"label","");
                break;
            }
        }
        Map map = new HashMap<>();
        map.put("title",title);
        map.put("list",invitationService.getEnumList("FORUM", "ARTICLE"));
        msg.setData(map);
        return msg;
    }

    @RequestMapping("/frontend/Invitation/save")
    public Map saveInvitation(@RequestBody Map map ,HttpServletRequest request) {
        Map map1 = new HashMap<>();
        try {
            User user = CurrentUser.getUser();
            if(user !=null){
                String type = StringUtils.transitionString(map, "type", "");
                String title = StringUtils.transitionString(map, "title", "");
                String content = StringUtils.transitionString(map, "content", "");
                int edition = NumberTool.transitionInteger(map, "edition", 0);
                Invitation invitation = new Invitation();
                invitation.setOrgid(user.getOrgid());
                invitation.setType(type);
                invitation.setTitle(title);
                invitation.setEdition(edition);
                invitation.setContent(content);
                invitationService.save(invitation);
                map1.put("message", "SUCCESS");
                map1.put("objid", invitation.getObjid());
            }else{
                map1.put("message", "ERROR");
            }

        }catch (Exception e){
            map1.put("message", "ERROR");
            e.printStackTrace();
        }
        return map1;
    }

    @RequestMapping("/frontend/Invitation/loadpade")
    public ResultMsg loadpade(@RequestBody Map param) {
        int edition = NumberTool.transitionInteger(param,"edition",1);
        List<Map> list = invitationService.getEnum("FORUM", "EDITION");
        for(int i=0;i<list.size();i++){
            Map map = list.get(i);
            if( i == edition-1){
                map.put("class","button-style");//给一个默认的class
            }else{
                map.put("class","");
            }
        }
        ResultMsg msg = new ResultMsg();
        msg.setData(list);
        return msg;
    }
    @RequestMapping("/frontend/Invitation/delete")
    public ResultMsg delete(@RequestBody Map param){
        ResultMsg resultMsg = new ResultMsg();
        try {
            int id = NumberTool.transitionInteger(param,"id",0);
            invitationService.delete(id);
            resultMsg.setMessage("SUCCESS");
        }catch (Exception e){
            e.printStackTrace();
            resultMsg.setMessage("ERROR");
        }
        return resultMsg;
    }
}
