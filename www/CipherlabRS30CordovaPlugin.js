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
var exec = require('cordova/exec');

var CipherlabRS30CordovaPlugin = function (require, exports, module) {

  function EchoService() {

    this.echo = function (str, callback) {
		cordova.exec(callback, function (err) {
		}, "CipherlabRS30CordovaPlugin", "echo", [str]);
    }

	this.getServiceVersion = function (callback) {
		cordova.exec(callback, function (err) {
		}, "CipherlabRS30CordovaPlugin", "getServiceVersion", []);
    }

    this.initialise = function (callback) {
		cordova.exec(callback, function (err) {
		}, "CipherlabRS30CordovaPlugin", "initialise", []);
    }

    this.destroy = function (callback) {
		cordova.exec(callback, function (err) {
		}, "CipherlabRS30CordovaPlugin", "destroy", []);
    }

	this.setReceiveScanCallback = function (callback) {
		var barcodes = this.barcodeTypes;
		var cb = function(data) {
			data.format = "Unknown";
			for(var key in barcodes) {
				if(barcodes[key] == parseInt(data.formatID)) {
					data.format = key;
					break;
				}
			}
			callback(data);
  			}
		cordova.exec(cb, function (err) {
		}, "CipherlabRS30CordovaPlugin", "setReceiveScanCallback", []);
	}

	this.requestScan = function(callback) {
		cordova.exec(callback, function (err) {
		}, "CipherlabRS30CordovaPlugin", "requestScan", []);
	}

    this.barcodeTypes = {
    	"Unknown": 0,
    	"Composite_CC_A": 47,
    	"Composite_CC_B": 55,
    	"Korean_3_of_5": 56,
    	"ISSN": 57,
    	"ISBT_128_Concatenation": 63,
    	"ISBT_128": 64,
    	"Code_39": 65,
    	"Italian_Pharmacode": 66,
    	"French_Pharmacode": 67,
    	"Interleaved_2_of_5": 69,
    	"Matrix_2_of_5": 70,
    	"Codabar": 71,
    	"Code_93": 72,
    	"Code_128": 73,
    	"UPC_E0": 74,
    	"UPC_E0_with_Addon_2": 75,
    	"UPC_E0_with_Addon_5": 76,
    	"EAN8": 77,
    	"EAN8_with_Addon_2": 78,
    	"EAN8_with_Addon_5": 79,
    	"EAN13": 80,
    	"EAN13_with_Addon_2": 81,
    	"EAN13_with_Addon_5": 82,
    	"MSI": 83,
    	"Plessey": 84,
    	"EAN_128": 85,
    	"Telepen": 90,
    	"GS1_DataBar14": 91,
    	"GS1_DataBarLimited": 92,
    	"GS1_DataBarExpanded": 93,
    	"UPC_A": 94,
    	"UPC_A_with_Addon_2": 95,
    	"UPC_A_with_Addon_5": 96,
    	"UPC_E1": 97,
    	"UPC_E1_with_Addon_2": 98,
    	"UPC_E1_with_Addon_5": 99,
    	"TLC_39": 100,
    	"Trioptic_Code_39": 101,
    	"Bookland": 102,
    	"Code_11": 103,
    	"Code_39_Full_ASCII": 104,
    	"IATA25": 105,
    	"Industrial_2_of_5": 106,
    	"PDF417": 107,
    	"Micro_PDF": 108,
    	"Data_Matrix": 109,
    	"Maxicode": 110,
    	"QR_Code": 111,
    	"US_Postnet": 112,
    	"US_Planet": 113,
    	"UK_Postal": 114,
    	"Japan_Postal": 115,
    	"Australian_Postal": 116,
    	"Dutch_Postal": 117,
    	"Composite_CC_C": 118,
    	"Macro_PDF": 119,
    	"Coupon_Code": 120,
    	"Chinese_2_of_5": 121,
    	"Aztec": 122,
    	"Micro_QR_Code": 123,
    	"USPS_4CB_One_Code_Intelligent_Mail": 124,
    	"UPU_FICS_Postal": 125,
    	"Macro_Micro_PDF417": 126
    }

  }

  module.exports = new EchoService();
}

CipherlabRS30CordovaPlugin(require, exports, module);

cordova.define("cordova/plugin/EchoService", CipherlabRS30CordovaPlugin);
