package sda.project.SRAD.Services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;





@Service
public class FileStorageService {

    // The directory where uploaded files will be stored
    private final Path rootLocation = Paths.get("uploads");


    // Store a file
	public void store(MultipartFile file) throws IOException {
		try {
			if (file.isEmpty()) 
				throw new IOException("Failed to store empty file.");

			Path destinationFile = this.rootLocation
                .resolve( Paths.get(file.getOriginalFilename()) )
                .normalize()
                .toAbsolutePath();

            // This is a security check
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) 
				throw new IOException("Cannot store file outside current directory.");
            
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new IOException("Failed to store file.", e);
		}
	}

    

    // Returns a list of all the files in the upload directory as Paths
	public Stream<Path> loadAll() throws IOException {
		try {
			return Files.walk(this.rootLocation, 1)
				.filter(path -> !path.equals(this.rootLocation))
				.map(this.rootLocation::relativize);
		}
		catch (IOException e) {
			throw new IOException("Failed to read stored files", e);
		}

	}


    // Returns a Path to the file with the given filename
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}


    // Returns a Resource to the file with the given filename
	public Resource loadAsResource(String filename) throws IOException {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable())
				return resource;
			else
				throw new IOException("Could not read file: " + filename);
		}
		catch (Exception e) {
			throw new IOException("Could not read file: " + filename, e);
		}
	}


    // Deletes all files in the upload directory. Useful when you want to clear the upload directory
    // when restarting your application
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}


    // Delete a file with the given filename
    public void delete(String filename) throws IOException {
        try {
            Path file = load(filename);
            Files.delete(file);
        }
        catch (IOException e) {
            throw new IOException("Could not delete file: " + filename, e);
        }
    }


    // Init method to create the upload directory if it does not exist
	public void init() throws IOException {
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new IOException("Could not initialize storage", e);
		}
	}
}