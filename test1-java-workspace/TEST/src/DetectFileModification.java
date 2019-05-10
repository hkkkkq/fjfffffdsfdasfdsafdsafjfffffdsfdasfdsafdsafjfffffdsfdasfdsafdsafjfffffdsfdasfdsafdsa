import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class DetectFileModification {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TimerTask timerTask = new FileWatcher(new File("E:\\test\\WatchService")) {
			
			@Override
			protected void onChange(File file) {
				// TODO Auto-generated method stub
				
				System.out.println("File(" + file.getName() + ") has changed (" + file.lastModified() + ")");
				
			}
		};
		
		Timer timer = new Timer();
		timer.schedule(timerTask, new Date(), 3000);
		
		

	}
	
	
	abstract static class FileWatcher extends TimerTask {
		
		private long timeStamp;
		private File file;
		
		public FileWatcher(File file) {
			
			this.file = file;
			this.timeStamp = file.lastModified();
			
		}
		
		public void run() {
			
			long timeStamp = file.lastModified();
			
			if (this.timeStamp != timeStamp) {
				this.timeStamp = timeStamp;
				onChange(file);
			}
		}
		
		protected abstract void onChange(File file);
		
		
		
	}

}
