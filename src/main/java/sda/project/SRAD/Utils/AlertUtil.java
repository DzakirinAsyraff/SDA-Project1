package sda.project.SRAD.Utils;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class AlertUtil {
    
    public static void alertSuccess(RedirectAttributes redirAttr, String message) {
        redirAttr.addFlashAttribute("alertSuccess", message);
    }

    public static void alertDanger(RedirectAttributes redirAttr, String message) {
        redirAttr.addFlashAttribute("alertDanger", message);
    }

    public static void alertPrimary(RedirectAttributes redirAttr, String message) {
        redirAttr.addFlashAttribute("alertPrimary", message);
    }

    public static void alertSecondary(RedirectAttributes redirAttr, String message) {
        redirAttr.addFlashAttribute("alertSecondary", message);
    }

    public static void alertWarning(RedirectAttributes redirAttr, String message) {
        redirAttr.addFlashAttribute("alertWarning", message);
    }

    public static void alertInfo(RedirectAttributes redirAttr, String message) {
        redirAttr.addFlashAttribute("alertInfo", message);
    }

    public static void alertDark(RedirectAttributes redirAttr, String message) {
        redirAttr.addFlashAttribute("alertDark", message);
    }

    public static void alertLight(RedirectAttributes redirAttr, String message) {
        redirAttr.addFlashAttribute("alertLight", message);
    }
}
