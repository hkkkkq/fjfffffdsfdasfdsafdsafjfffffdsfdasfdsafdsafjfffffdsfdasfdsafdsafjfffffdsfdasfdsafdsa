package com.henc.cdrs.common.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import lombok.extern.slf4j.Slf4j;

/**
 * 파일 다운로드를 위한 전용 View class 이다.
 * 해당 view를 사용하기 위해서는 Controller 클래스내 메소드에서 ModelAndView 클래스를 리턴해야 한다.
 * (e.g. kr.ac.suwon.cm.file.web.FileBasController.deleteFileBas(@RequestParam int fileSeq, Model model) 참고
 *
 * @author 조용상
 * @version 1.0
 * <pre>
 * 수정일                수정자         수정내용
 * ---------------------------------------------------------------------
 * </pre>
 */
@Slf4j
@Component
public class FileDownloadView extends AbstractView {
    public static final String FILE_ATTRIBUTE_NAME = "downloadFile";
    public static final String FILENAME_ATTRIBUTE_NAME = "originalFilename";

    private File file = null;
    private String filename = null;

    public FileDownloadView() {
        setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
    }

    public FileDownloadView(File file) {
        this(file, file.getName());
    }

    public FileDownloadView(File file, String filename) {
        this.file = file;
        this.filename = filename;
    }

    /**
     * AbstractView의 renderMergedOutputModel() 메소드를 오버라이드 함
     * @see org.springframework.web.servlet.view.AbstractView#renderMergedOutputModel(java.util.Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        file = (model.get(FILE_ATTRIBUTE_NAME) instanceof File) ? (File) model.get(FILE_ATTRIBUTE_NAME) : file;
        if (file == null && model.get(FILE_ATTRIBUTE_NAME) instanceof File) {
            file = (File) model.get(FILE_ATTRIBUTE_NAME);
        }
        if (filename == null && model.get(FILENAME_ATTRIBUTE_NAME) != null) {
            filename = (String) model.get(FILENAME_ATTRIBUTE_NAME);
        }

        InputStream is = null;
        OutputStream out = response.getOutputStream();
        int size = -1;
        try {
            is = new FileInputStream(file);
            size = (int) file.length();

            response.setContentType(getContentType());
            response.setContentLength(size);
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(filename, "utf-8") + "\";");

            FileCopyUtils.copy(is, out);
        } finally {
            if(is != null){
                try{
                    is.close();
                } catch(Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        out.flush();
    }
}