package com.fis.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class StringEx {
	public static String LPad(String str, Integer length, char car) {
		return str + String.format("%" + (length - str.length()) + "s", "").replace(" ", String.valueOf(car));
	}

	public static String RPad(String str, Integer length, char car) {
		return String.format("%" + (length - str.length()) + "s", "").replace(" ", String.valueOf(car)) + str;
	}

	public static String RPad(String str, Integer length) {
		return String.format("%1$-" + length + "s", str);
	}

	public static String Pad(String sInput, Integer length, String sClone) {
		return sInput + String.format("%" + (length - sInput.length()) + "s", "").replace(" ", sClone);
	}

	public static String Trim(Object oInput) {
		if (oInput == null || oInput.toString().isEmpty())
			return "";
		return oInput.toString().trim();
	}

	public static String ToString(Object oInput) {
		if (oInput == null || oInput.toString().isEmpty())
			return "";
		return oInput.toString();
	}

	public static String GetIsNullValue(Object oInput) {
		if (Trim(oInput).isEmpty())
			return " ";
		return Trim(oInput);
	}

	public static Boolean IsNullOrEmpty(Object oInput) {
		if (oInput == null || Trim(oInput).isEmpty())
			return true;
		else
			return false;
	}

	// ThangNM11 05/04/2017 - Check unicode, ký tự xuống dòng, tab, ký tự
	// đặc biệt
	public static String getValidateJsonArrayUnicodeSpecialCharacters(JsonArray jArrayInput, String sKey) {
		String sRet = "";
		if (jArrayInput != null && jArrayInput.size() > 0) {
			String sTemp = "";
			for (int i = 0; i < jArrayInput.size(); i++) {
				JsonElement je = jArrayInput.get(i).getAsJsonObject().get(StringEx.Trim(sKey));
				if (je != null) {
					sTemp = getValidateStringUnicodeSpecialCharacters(je.getAsString());
					if (!IsNullOrEmpty(sTemp))
						sRet += ", [" + sTemp + "]";
				}
			}
			if (sRet.startsWith(","))
				sRet = sRet.substring(1);
		}
		return sRet;
	}

	public static String getValidateStringUnicodeSpecialCharacters(String sInput) {
		// ThangNM11 - 16/08/2017 - A.Thành HQ yêu cầu thêm các ký tự đặc biệt:
		// \/-.*()
		String sRet = "";
		if (!StringEx.IsNullOrEmpty(sInput)) {
			if (sInput.contains("\n"))// ("\n") || sInput.endsWith("\n")
				return sInput;
			sInput = StringEx.Trim(sInput);
			sInput = sInput.replace('\\', 'a');
			sInput = sInput.replaceAll("/", "");
			sInput = sInput.replaceAll(" ", "");
			Pattern rgPattern = Pattern.compile("[a-zA-Z0-9-.*()&_]");// \s+.,_-
			Matcher rgMatcher = rgPattern.matcher(sInput);
			if (rgMatcher.replaceAll("").length() > 0)
				sRet = sInput;
		}
		return sRet;
	}

	public static List<String> SplitStringByMaxLength(String sInput, Character cSplit, int iMaxLength) {
		String sSplit = String.valueOf(cSplit);
		List<String> lstTemp = new ArrayList<String>();
		sInput = StringEx.Trim(sInput);
		int iLength = 0;
		if (sInput.length() > iMaxLength) {
			while (sInput.length() > 0) {
				if (sInput.length() <= iMaxLength) {
					lstTemp.add(RemoveSplitOther(sInput, cSplit));
					sInput = "";
				} else {
					iLength = sInput.substring(0, iMaxLength).lastIndexOf(sSplit);
					if (iLength < 0 || iLength > iMaxLength) {
						return null;
					} else {
						lstTemp.add(RemoveSplitOther(sInput.substring(0, iLength + 1), cSplit));
						sInput = sInput.substring(iLength + 1);
					}
				}
			}
		} else {
			lstTemp.add(RemoveSplitOther(sInput, cSplit));
		}
		return lstTemp;
	}

	public static String RemoveSplitOther(String sInput, Character cSplit) {
		String sSplit = String.valueOf(cSplit);
		sInput = StringEx.Trim(sInput);
		while (sInput.startsWith(sSplit)) {
			sInput = sInput.substring(1);
		}
		while (sInput.endsWith(sSplit)) {
			sInput = sInput.substring(0, sInput.length() - 1);
		}

		Pattern rgPattern = Pattern.compile(String.format("\\%s+", sSplit));
		Matcher rgMatcher = rgPattern.matcher(sInput);
		sInput = rgMatcher.replaceAll(sSplit);

		return sInput;
	}

	public static String getPartInList(List<String> lstTemp, int iPart) {
		String sRet = "";
		if (lstTemp != null && lstTemp.size() > 0) {
			if (iPart > 0) {
				if (iPart <= lstTemp.size())
					sRet = lstTemp.get(iPart - 1);
			}
		}
		return sRet;
	}

	public static List<String> SplitStringByChar(String sInput, Character cSplit, Boolean bRemoveDuplicate) {
		List<String> lstRet = new ArrayList<String>();
		sInput = StringEx.Trim(sInput);
		for (String sItem : sInput.split(String.valueOf(cSplit))) {
			sItem = StringEx.Trim(sItem).toUpperCase();
			if (!StringEx.IsNullOrEmpty(sItem)) {
				if (bRemoveDuplicate) {
					if (lstRet.indexOf(sItem) < 0)
						lstRet.add(sItem);
				} else {
					lstRet.add(sItem);
				}
			}
		}
		return lstRet;
	}

	protected static Boolean getValidateJsonArrayIsNullOrEmpty(JsonArray jArrayInput, String sKey) {
		if (jArrayInput != null && jArrayInput.size() > 0) {
			for (int i = 0; i < jArrayInput.size(); i++) {
				JsonElement je = jArrayInput.get(i).getAsJsonObject().get(StringEx.Trim(sKey));
				if (je != null) {
					if (IsNullOrEmpty(je.getAsString()))
						return true;
				} else
					return true;
			}
		}
		return false;
	}

	public static BigDecimal str2decimal(String str) {
		BigDecimal str2decimal = new BigDecimal(str);
		return str2decimal;
	}
}
