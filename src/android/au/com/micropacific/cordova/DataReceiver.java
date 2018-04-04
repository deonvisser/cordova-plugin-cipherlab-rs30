/*
The MIT License (MIT)
Copyright (c) 2015 Michael Ribbons
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package au.com.micropacific.cordova;

import com.cipherlab.barcode.*;
import com.cipherlab.barcode.decoder.*;
import com.cipherlab.barcode.decoderparams.*;
import com.cipherlab.barcodebase.*;

import android.util.Log;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.*;

/// Create a broadcast object to receive the intent coming from service.
public class DataReceiver extends BroadcastReceiver {

	private ReaderManager mReaderManager;
	private CipherlabRS30Plugin plugin;

	public DataReceiver(CipherlabRS30Plugin _plugin, ReaderManager _ReaderManager)
	{
		this.mReaderManager = _ReaderManager;
		this.plugin = _plugin;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// If intent of the Intent_SOFTTRIGGER_DATA string is received
		if (intent.getAction().equals(GeneralString.Intent_SOFTTRIGGER_DATA)) {
				
			// fetch the data within the intent
			String data = intent.getStringExtra(GeneralString.BcReaderData);
			byte[] byteData = intent.getByteArrayExtra(GeneralString.BcReaderDataArray);
			int iCodeType = intent.getIntExtra(GeneralString.BcReaderCodeType, 0);
				
			// display the fetched data
			//e1.setText(data);
			Log.v("CipherlabRS30Plugin", "got data, 1: " + data);
			this.plugin.receieveScan(data,byteData,iCodeType);
			
		} else if (intent.getAction().equals(GeneralString.Intent_PASS_TO_APP)){
				
			// fetch the data within the intent
			String data = intent.getStringExtra(GeneralString.BcReaderData);
			byte[] byteData = intent.getByteArrayExtra(GeneralString.BcReaderDataArray);
			int iCodeType = intent.getIntExtra(GeneralString.BcReaderCodeType, 0);
				
			// display the fetched data
			//e1.setText(data);
			Log.v("CipherlabRS30Plugin", "got data, 2: " + data);
			this.plugin.receieveScan(data,byteData,iCodeType);
				
		} else if (intent.getAction().equals(GeneralString.Intent_READERSERVICE_CONNECTED)){
			try {
				BcReaderType myReaderType =  mReaderManager.GetReaderType();	
				//e1.setText(myReaderType.toString());
				
				ReaderOutputConfiguration settings = new ReaderOutputConfiguration();
				mReaderManager.Get_ReaderOutputConfiguration(settings);
				settings.autoEnterWay = OutputEnterWay.Disable; 
				settings.autoEnterChar = OutputEnterChar.None; 
				settings.showCodeType = Enable_State.FALSE;
				settings.showCodeLen = Enable_State.FALSE;
			
				// ADDED TO STOP EXTRA CHARACTERS BEING ADDED WHEN TICK BOX 'Transmit AIM Code ID' is true
				// settings.transmitCodeIdChar = TransmitCodeIDType.None;				
				// settings.transmitCheckDigit = Enable_State.FALSE;
			
				settings.enableKeyboardEmulation = KeyboardEmulationType.None;
				
				// For old readerService
				// settings.enableKeyboardEmulation = Enable_State.FALSE;
				
				mReaderManager.Set_ReaderOutputConfiguration(settings);
				
				//Maybe check the settings are set successfully set?
				
				// If successful, it returns ClResult.S_ERR. Otherwise, return other values.
				// ClResult clRet = mReaderManager.Set_ReaderOutputConfiguration(settings);
				// if (ClResult.S_ERR == clRet){
				// 	Log.v("CipherlabRS30Plugin", "Set_ReaderOutputConfiguration was failed");
				// } else if (ClResult.Err_InvalidParameter == clRet){
				// 	Log.v("CipherlabRS30Plugin", "Set_ReaderOutputConfiguration was InvalidParameter");
				// } else if (ClResult.Err_NotSupport == clRet){
				// 	Log.v("CipherlabRS30Plugin", "Set_ReaderOutputConfiguration was NotSupport");
				// } else if (ClResult.S_OK == clRet){
				// 	Log.v("CipherlabRS30Plugin", "Set_ReaderOutputConfiguration was Ok");
				// }				
				
				Log.v("CipherlabRS30Plugin", "got data, 3");
			}
			catch(Exception e){
				
				StringWriter writer = new StringWriter();
				PrintWriter printWriter = new PrintWriter( writer );
				e.printStackTrace( printWriter );
				printWriter.flush();
	
				String stackTrace = writer.toString();
	
				Log.v("CipherlabRS30Plugin", "Error initialising reader service: " + stackTrace);
			}
		}

	}
};
