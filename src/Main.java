import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

	//Simple example of how to use BlockGenerator module
	
	public static void main(String[] args) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime now = LocalDateTime.now();
		String dtStr = dtf.format(now);
		
		BlockGenerator.compileBlock(Long.parseLong(dtStr), 0, "c9a2ebe3c4a631b66d03095553db3370466d5fb8122ce1485992e83338864947", "b42c47ef6b54655f061cb40201eaf68e7c97abc6b8f12584526585347663bfd1");
	}

}
