package com.clamaud.compta.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.clamaud.compta.entity.Account;
import com.clamaud.compta.entity.UploadForm;
import com.clamaud.compta.services.AccountService;
import com.clamaud.compta.services.ImportService;



@Controller
public class ComptaImportController {
	
	@Autowired
	private ImportService importService;
	
	
	@Autowired 
	private AccountService accountService;
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
    @GetMapping("/uploadOneFile")
    public String uploadOneFileHandler(Model model) {
 
    	UploadForm uploadForm = new UploadForm();
        model.addAttribute("uploadForm", uploadForm);
 
      return "uploadOneFile";
    }
	
	@PostMapping("/uploadOneFile")
    public String uploadOneFileHandlerPOST(HttpServletRequest request,
         Model model,
         @ModelAttribute("uploadForm") UploadForm uploadForm) {
 
      return this.doUpload(request, model, uploadForm);
 
    }
	
	private String doUpload(HttpServletRequest request, Model model, //
			UploadForm accountForm) {
	 
	      String description = accountForm.getDescription();
	      System.out.println("Description: " + description);
	 
	      // Root Directory.
	      String uploadRootPath = request.getServletContext().getRealPath("upload");
	      System.out.println("uploadRootPath=" + uploadRootPath);
	 
	      File uploadRootDir = new File(uploadRootPath);
	      // Create directory if it not exists.
	      if (!uploadRootDir.exists()) {
	         uploadRootDir.mkdirs();
	      }
	      MultipartFile[] fileDatas = accountForm.getFileDatas();
	      //
	      List<File> uploadedFiles = new ArrayList<File>();
	      List<String> failedFiles = new ArrayList<String>();
	 
	      for (MultipartFile fileData : fileDatas) {
	 
	         // Client File Name
	         String name = fileData.getOriginalFilename();
	         System.out.println("Client File Name = " + name);
	 
	         if (name != null && name.length() > 0) {
	            try {
	               // Create the file at server
	               File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);
	               
	               
	               BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
	               stream.write(fileData.getBytes());
	               stream.close();
	               //
	               List<Account> accounts = importService.getAccountsFromFile(serverFile);
	               accountService.saveAccounts(accounts);
	               
	               uploadedFiles.add(serverFile);
	               System.out.println("Write file: " + serverFile);
	            } catch (Exception e) {
	               System.out.println("Error Write file: " + name);
	               System.out.println("Message: " + e.getMessage());
	               e.printStackTrace();
	               failedFiles.add(name);
	            }
	         }
	      }
	      model.addAttribute("description", description);
	      model.addAttribute("uploadedFiles", uploadedFiles);
	      model.addAttribute("failedFiles", failedFiles);
	      return "uploadResult";
	   }

}
