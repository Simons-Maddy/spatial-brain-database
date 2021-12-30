package com.spatial.spatialbrain.controller;


import com.fasterxml.jackson.annotation.JsonValue;
import net.minidev.json.JSONValue;
import org.json.JSONArray;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.spatial.spatialbrain.pojo.SlideseqLocation;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Controller
public class SlideseqController {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/slideseq")
    public String searchslideseq(Model m) throws Exception {
        try {
            /*
            String sql = "SELECT * FROM scp815_brain_locations";
            List<SlideseqLocation> locList = jdbcTemplate.query(sql,
                    new RowMapper<SlideseqLocation>() {
                        SlideseqLocation loc = null;

                        @Override
                        public SlideseqLocation mapRow(ResultSet rs, int rowNum) throws SQLException {
                            loc = new SlideseqLocation();
                            loc.setBarcodes(rs.getString("barcodes"));
                            loc.setYcoord(rs.getDouble("ycoord"));
                            loc.setXcoord(rs.getDouble("xcoord"));
                            return loc;
                        }
                    }
            );
            int number = locList.size();
            String loclist = JSONValue.toJSONString(locList);

            m.addAttribute("locations", loclist);
            m.addAttribute("number", number);
            */
            return "slideseq";
        }catch (Exception e){
            throw new Exception(e);
        }
    }
}

