package com.henc.cdrs.mgmt.progress.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henc.cdrs.mgmt.progress.model.Progress;
import com.henc.cdrs.mgmt.progress.repository.ProgressMapper;
import com.henc.dream.domain.IBSheetRowStatus;
import com.henc.dream.exception.UnsupportedRowTypeException;
import com.henc.dream.util.CamelCaseMap;

@Transactional
@Service
public class ProgressService {

    @Autowired
    private ProgressMapper progressMapper;

    public List<CamelCaseMap> selectProgressGrdList(Progress progress) {
        return progressMapper.selectProgressGrdList(progress);
    }

    public void saveProgressList(List<Progress> progresses) {
        if (CollectionUtils.isNotEmpty(progresses)) {
            for (Progress progress : progresses) {
                switch (progress.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:
                        progressMapper.insertProgress(progress);
                        break;
                    case IBSheetRowStatus.UPDATED:
                        progressMapper.updateProgress(progress);
                        break;
                    case IBSheetRowStatus.DELETED:
                        progressMapper.deleteProgress(progress);
                        break;
                    default:
                        throw new UnsupportedRowTypeException(progress.getRowStatus());
                }
            }
        }
    }
}
