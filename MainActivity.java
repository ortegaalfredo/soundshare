package com.example.myfirstapp;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.groundworkstech.api.Metadata;
import com.groundworkstech.api.OnContent;
import com.groundworkstech.api.OnMetadata;
import com.groundworkstech.api.Service;

public class MainActivity extends Activity {
	Service sc;
	private static final String DEBUG_TAG = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
    	sc = new Service("APIKEY");
    	final Activity activity=this;
    	sc.registerCallback(new OnMetadata(){
    				public void run(Metadata arg, Service service){
    					final Metadata metadata=arg;
    					activity.runOnUiThread(new Runnable() {
    					    public void run() {
    					        Toast.makeText(activity, "Downloading file:"+metadata.filename, Toast.LENGTH_SHORT).show();
    					    }
    					});
    					// Download file content...
    					service.getContent(arg);
    				}
    			}
    	);
    	sc.registerCallback(new OnContent(){
			public void run(Metadata arg, Service service){
				final Metadata metadata=arg;
				activity.runOnUiThread(new Runnable() {
				    public void run() {
				        Toast.makeText(activity, "Downloading file:"+metadata.filename, Toast.LENGTH_SHORT).show();
				    }
				});
			}
		}
);

    	/*sc.registerCallback(new OnContent(){
			@Override
			public void run(Metadata _metadata){
				//....    					
			}
		});*/
    	// start audio capture
    		sc.startListening();

        //initBackgroundChanger();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public class InvalidContentException extends Exception {
    	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public InvalidContentException(String message) {
    		super(message);
    	}
    }

    public void sendAction(View view) {
    	//sc.playSound("/mnt/sdcard/audio.bin");
    	sc.uploadFileAndPlayAudio("/mnt/sdcard/test.bin");
    	
    	/*Context context = getApplicationContext();
    	CharSequence text = "Not implemented!";
    	int duration = Toast.LENGTH_SHORT;

    	Toast toast = Toast.makeText(context, text, duration);
    	toast.show();*/
    }
    

    private int[] backgrounds = {
			  R.drawable.drawable_1,
			  R.drawable.drawable_2,
			  R.drawable.drawable_3,
			  R.drawable.drawable_4,
			  R.drawable.drawable_5
	  };

	  void initBackgroundChanger() {
		  LinearLayout  linearLayout = (LinearLayout) findViewById(R.id.mainActivity);
		  linearLayout.setBackgroundResource(backgrounds[randInt(0, backgrounds.length - 1)]);
	  }
	  
	  /**
	   * Returns a pseudo-random number between min and max, inclusive.
	   * The difference between min and max can be at most
	   * <code>Integer.MAX_VALUE - 1</code>.
	   *
	   * @param min Minimum value
	   * @param max Maximum value.  Must be greater than min.
	   * @return Integer between min and max, inclusive.
	   * @see java.util.Random#nextInt(int)
	   */
	  public static int randInt(int min, int max) {

	      // Usually this can be a field rather than a method variable
	      Random rand = new Random();

	      // nextInt is normally exclusive of the top value,
	      // so add 1 to make it inclusive
	      int randomNum = rand.nextInt((max - min) + 1) + min;

	      return randomNum;
	  }
}