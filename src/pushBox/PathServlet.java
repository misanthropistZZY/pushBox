package pushBox;

import com.google.gson.Gson;
import pushBox.beans.boxPath.Stage;
import pushBox.beans.boxPath.StageQueue;
import pushBox.beans.caches.NodeCache;
import pushBox.beans.caches.StageCach;
import pushBox.beans.common.Definition;
import pushBox.beans.errorInfo.ResponseGenerator;
import pushBox.beans.pojo.People;
import pushBox.beans.pojo.Result;
import pushBox.util.PathUtil;
import pushBox.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author:Zzy
 * @Date: Crated in 2017-11-30 12:26
 * @Description:
 */

@WebServlet("/getPath")
public class PathServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResponseGenerator r = new ResponseGenerator();
        String mapInfo = req.getParameter("mapInfo");
        String boxInfo = req.getParameter("boxInfo");
        String peopleInfo = req.getParameter("peopleInfo");

        Gson gson = new Gson();
        People people = gson.fromJson(peopleInfo, People.class);
        System.out.println(people.X);
        System.out.println("mapInfo:" + mapInfo);
        System.out.println("boxInfo:" + boxInfo);
        System.out.println("peopleInfo:" + peopleInfo);
        int[] boxList = Util.getList(boxInfo);
        int[] pointList = Util.getList(mapInfo);
        int[][] transformMap = new int[Definition.EDGE_SIZE][Definition.EDGE_SIZE];
        for (int i = 0; i < Definition.EDGE_SIZE; i++) {
            for (int j = 0; j < Definition.EDGE_SIZE; j++) {
                transformMap[i][j] = pointList[i * Definition.EDGE_SIZE + j];
            }
        }
        int[][] realMap = PathUtil.formulateMap(transformMap, boxList, people);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(realMap[i][j] + "   ");
            }
            System.out.println();
        }

        PathUtil.initMap(realMap);
        Stage targetStage = PathUtil.caculatePath();
        if (targetStage == null) {
            r.setCode("0");
            System.out.println("无解！");
        } else {
            r.setCode("1");
            Result result = new Result();
            result.setPath(targetStage.getStepDetail());
            result.setStepCount(targetStage.getStepCount());
            result.setSearchCount(StageQueue.stageCount);
            //r.setBody(targetStage.getStepDetail());
            r.setBody(result);
            System.out.println("找到路径！");
            System.out.println("Path：" + targetStage.getStepDetail());
        }

        System.out.println();
        System.out.println();
        //System.out.println("boxInfo:" + boxInfo);
        //System.out.println("peopleInfo:" + peopleInfo);

        //r.setCode("1");
        //r.setBody("UUUDR");
        NodeCache.cleanNode();
        StageCach.clean();
        StageQueue.clean();

        System.out.println("1111");
        String json = gson.toJson(r, ResponseGenerator.class);
        System.out.println("json:" + json);
        resp.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(json);
    }
}
