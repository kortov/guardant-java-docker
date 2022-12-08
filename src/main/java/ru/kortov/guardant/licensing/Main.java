package ru.kortov.guardant.licensing;

import grdlic.Feature;
import grdlic.GrdStatus;
import grdlic.GrdString;
import grdlic.GrdVendorCodes;
import grdlic.api;
import ru.kortov.guardant.licensing.exception.GrdlicException;
import ru.kortov.guardant.licensing.exception.GuardantProcessException;
import ru.kortov.guardant.licensing.system.NativeLibraryLoader;

import java.util.Arrays;
import java.util.HexFormat;

public class Main {
    public static void main(String[] args) {

        if (args.length != 4) {
            throw new GuardantProcessException(
                "You must provide 4 args: publicVendorCode, privateReadCode, featureNumber and publicFeatureKey");
        }

        System.out.println("Program args: " + Arrays.toString(args));

        int publicVendorCode = Integer.parseInt(args[0]);
        int privateReadVendorCode = Integer.parseInt(args[1]);
        int featureNumber = Integer.parseInt(args[2]);
        byte[] publicFeatureKey = HexFormat.ofDelimiter(" ").parseHex(args[3]);

        NativeLibraryLoader.loadLib();

        GrdVendorCodes codes = new GrdVendorCodes(publicVendorCode, privateReadVendorCode, 0);
        String visibility = "{\"remoteMode\": 3}";
        System.out.println("Get licenses info..");
        GrdString licensesListJson = new GrdString();
        GrdStatus status = api.GrdGetLicenseInfo(visibility, null, licensesListJson);
        errorHandling(status);
        System.out.printf("Found licenses: %s%n", licensesListJson.getValue());

        System.out.printf("Chosen feature number is: %s%n", featureNumber);
        Feature feature = new Feature(featureNumber);
        System.out.println("Try to login feature..");
        status = feature.Login(codes, visibility);
        errorHandling(status);

        System.out.println("Call check feature..");
        status = feature.Check(publicFeatureKey);
        errorHandling(status);

        System.out.println("Logout feature..");
        status = feature.Logout();
        errorHandling(status);
    }

    private static void errorHandling(GrdStatus status) {
        if (!status.equals(GrdStatus.OK)) {
            System.out.printf("Error! %s%n", status.getValue());
            throw new GrdlicException("Grdlic API error");
        } else {
            System.out.println("Success");
        }
    }
}
