package com.niugiaogiao.core.parse;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.niugiaogiao.core.enums.Platform;
import com.niugiaogiao.core.hotspot.HotSpotResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class ZhiHuParse implements HotSpotParse {

    @Override
    public boolean isParse(HotSpotResponse hotSpotResponse) {
        return Platform.ZHI_HU.equals(hotSpotResponse.getPlantFlag());
    }

    @Override
    public List<BaseParseResult> parse(JSONObject hotSpotResponse) {
        TransferDTO transferDTO = getTransferDTO(hotSpotResponse);
        List<BaseParseResult> result = new ArrayList<>(transferDTO.questions.size());
        for (QuestionDTO question : transferDTO.questions) {
            ZhiHuParseResult itemParseResult = new ZhiHuParseResult();
            itemParseResult.setAccessUrl(question.url);
            itemParseResult.setTitle(question.title);
            itemParseResult.setTopicId(IdUtil.simpleUUID());
            result.add(itemParseResult);
        }
        return result;
    }

    private TransferDTO getTransferDTO(JSONObject jsonObject) {
        JSONArray questionArray = jsonObject.getJSONArray("data");
        List<QuestionDTO> questions = new ArrayList<>(questionArray.size());
        for (Object o : questionArray) {
            JSONObject hotValObject = (JSONObject) o;
            JSONObject jsonQuestion = (JSONObject) hotValObject.get("question");
            questions.add(jsonQuestion.to(QuestionDTO.class));
        }
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setQuestions(questions);
        return transferDTO;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    static class ZhiHuParseResult extends BaseParseResult {
    }

    @Data
    static class QuestionDTO {
        private String url;
        private String title;
    }

    @Data
    static class ReactionDTO {
    }

    @Data
    static class TransferDTO {
        List<QuestionDTO> questions;
        List<ReactionDTO> reactions;
    }
}
