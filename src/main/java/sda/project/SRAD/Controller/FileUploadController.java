package sda.project.SRAD.Controller;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sda.project.SRAD.Services.FileStorageService;


// ! Just a example taken from https://spring.io/guides/gs/uploading-files/

@Controller
@RequestMapping("/upload")
public class FileUploadController {

    @Autowired
	private FileStorageService storageService;


	@GetMapping("")
	public String listUploadedFiles(Model model) throws IOException {

        // Read all the files from the upload directory and map them to a list of clickable urls
		model.addAttribute(
            "files", 
            storageService
                .loadAll("test")
                .map(path -> MvcUriComponentsBuilder
                    .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString()).build().toUri().toString()
                )
                .collect(Collectors.toList())
        );

		return "Misc/UploadForm";
	}


    @GetMapping("delete/{filename:.+}")
    public String deleteFile(
        @PathVariable String filename,
        RedirectAttributes redirectAttributes
    ) throws IOException {

        storageService.delete(filename, "test");
        redirectAttributes.addFlashAttribute("message", "You successfully deleted " + filename + "!");

        return "redirect:/upload";
    }


	@GetMapping("files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(
        @PathVariable String filename
    ) throws IOException {

        // Serves the file with the given filename, usually results in a download
		Resource file = storageService.loadAsResource(filename, "test");
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@PostMapping("")
	public String handleFileUpload(
        @RequestParam("file") MultipartFile file,
		RedirectAttributes redirectAttributes
    ) throws IOException {

		storageService.store(file, "test");
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/upload";
	}
}