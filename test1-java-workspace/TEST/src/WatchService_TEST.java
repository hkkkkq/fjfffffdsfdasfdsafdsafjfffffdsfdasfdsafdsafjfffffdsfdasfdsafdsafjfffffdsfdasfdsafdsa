import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;


public class WatchService_TEST {
	
	static String result = "111111111111111";


	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub	
		
		printResult();
		System.out.println("");
		
		System.setProperty("user.dir", "E:\\test\\WatchService");
		System.out.println(System.getProperty("user.dir"));
//		System.out.println(System.getProperty("user.home"));
			
		Path path = Paths.get("E:\\test\\WatchService");
//		Path path = Paths.get("/home/infadm");
	
	//	System.out.println(path.getNameCount());
	//	System.out.println(path.toString());
	//	System.out.println(path.getFileSystem());
	//	System.out.println(path.getFileSystem().getSeparator());
	//	System.out.println(path.getName(0));
	//	System.out.println(path.getName(1));
	//	System.out.println(path.getFileName());
	//	System.out.println(path.getParent());
	//	System.out.println(path.getRoot());
	//	
	//	Iterator i = path.iterator();
	//	
	//	while (i.hasNext()) {
	//		System.out.println(i.next());
	//	}
		
	//	System.out.println(path.isAbsolute());
			
		WatchService watchService = path.getFileSystem().newWatchService();
		
		path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
		
		while (true) {
					
			WatchKey watchKey = watchService.take();
			
	//		System.out.println(watchKey);
	//		System.out.println(watchKey.toString());
							
			for (WatchEvent<?> event:watchKey.pollEvents()) {
				
				System.out.println("****************************");
				System.out.println(event.count());
				System.out.println(event.toString());
				System.out.println(event.context());
				System.out.println(event.kind());
	//			System.out.println(event.kind().name());
	//			System.out.println(event.kind().toString());
				
//				Path p = ((WatchEvent<Path>) event).context();
				Path p = (Path) event.context();
//				Path child = path.resolve(p);
				
							
				System.out.println(p.toAbsolutePath());
				
				if (event.kind().toString().equals("ENTRY_CREATE") || event.kind().toString().equals("ENTRY_MODIFY")) {
//				if (event.kind().toString().equals("ENTRY_MODIFY")) {
					
//					BufferedReader br = null;
					
					try {
						
//						br = Files.newBufferedReader(child, Charset.forName("utf-8"));
						
						
						String line;
//						
//						while ((line=br.readLine()) != null) {
//							result = line;
//						}
						
//						line = new String(Files.readAllBytes(p.toAbsolutePath()));
						line = new String(Files.readAllBytes(p));
						result = line;
						
						printResult();
						
					} catch (Exception e) {
						System.out.println("* Exception *");
						e.printStackTrace();
						
					} finally {
//						if (br != null) {
//							br.close();
//						}
					}
					
				} 
				
//				System.out.println(p.toString());
//				System.out.println(p.getFileName());
//				System.out.println(child.toString());
//				System.out.println(child.getFileName());
				
				
				System.out.println("****************************");
				
			}
			
			//watchKey.reset();
			System.out.println(watchKey.reset());
			System.out.println("");
//			watchKey.cancel();
//			watchService.close();
//			
//			System.out.println("[3s Sleep]");
//			Thread.sleep(3000);
			
		}


	}
	
	public static void printResult() {
		System.out.println("*** result:" + result + " ***");
	}


}
