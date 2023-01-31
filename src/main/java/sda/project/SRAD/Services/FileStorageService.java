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
	public void store(MultipartFile file, String directory) throws IOException {
		try {
			Path destinationDirectory = this.rootLocation
				.resolve( Paths.get( directory ) )
				.normalize()
				.toAbsolutePath();
			
			// Empty files are not allowed
			if (file.isEmpty()) throw new IOException("Failed to store empty file.");

			// Create the directory if it doesn't exist
			if (!Files.exists(destinationDirectory)) 
				Files.createDirectories(destinationDirectory);

			// This is the path to the destination file
			Path destinationFile = destinationDirectory
                .resolve( Paths.get( file.getOriginalFilename() ) )
                .normalize()
                .toAbsolutePath();
            
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new IOException("Failed to store file.", e);
		}
	}

    

    // Returns a list of all the files in the upload directory as Paths
	public Stream<Path> loadAll(String directory) throws IOException {
		try {
			Path destinationDirectory = this.rootLocation
				.resolve( Paths.get( directory ) )
				.normalize()
				.toAbsolutePath();

			// Create the directory if it doesn't exist
			if (!Files.exists(destinationDirectory)) 
				Files.createDirectories(destinationDirectory);

			return Files.walk(destinationDirectory, 1)
				.filter(path -> !path.equals(destinationDirectory))
				.map(destinationDirectory::relativize);
		}
		catch (IOException e) {
			throw new IOException("Failed to read stored files", e);
		}

	}


    // Returns a Path to the file with the given filename
	public Path loadFilePath(String filename, String directory) {
		return rootLocation.resolve(directory).resolve(filename);
	}


    // Returns a Resource to the file with the given filename
	public Resource loadAsResource(String filename, String directory) throws IOException {
		try {
			Path file = loadFilePath(filename, directory);
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
    public void delete(String filename, String directory) throws IOException {
        try {
            Path file = loadFilePath(filename, directory);
            Files.delete(file);
        }
        catch (IOException e) {
            throw new IOException("Could not delete file: " + filename, e);
        }
    }


    // Init method to create the upload directory if it does not exist, also clears the directory
	public void init() throws IOException {
		try {
			deleteAll();
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new IOException("Could not initialize storage", e);
		}
	}
}