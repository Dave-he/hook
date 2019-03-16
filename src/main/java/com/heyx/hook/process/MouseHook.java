package com.heyx.hook.process;

import com.heyx.hook.entity.Action;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

public class MouseHook implements Runnable {

    private static final int WM_MOUSEMOVE = 512;
    private static WinUser.HHOOK hhk;
    private static LowLevelMouseProc mouseHook;
    final static User32 lib = User32.INSTANCE;
    private boolean [] on_off=null;

    MouseHook(boolean[] on_off){
        this.on_off = on_off;
    }

    public interface LowLevelMouseProc extends WinUser.HOOKPROC {
        WinDef.LRESULT callback(int nCode, WinDef.WPARAM wParam, MOUSEHOOKSTRUCT lParam);
    }

    public static class MOUSEHOOKSTRUCT extends Structure {
        public static class ByReference extends MOUSEHOOKSTRUCT implements
                Structure.ByReference {
        };
        public User32.POINT pt;
        public int wHitTestCode;
        public User32.ULONG_PTR dwExtraInfo;
        public User32.LPARAM pointer;
    }

    @Override
    public void run() {
        WinDef.HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
        mouseHook = new LowLevelMouseProc() {
            public WinDef.LRESULT callback(int nCode, WinDef.WPARAM wParam,
                                           MOUSEHOOKSTRUCT info) {
                Action action = new Action();
                if (!on_off[0]) {
                    System.exit(0);
                }
                if (nCode >= 0) {
                    if (wParam.intValue() == MouseHook.WM_MOUSEMOVE) {
                        String option = "x=" + info.pt.x +" y=" + info.pt.y;
                        //TODO
                        action.setOperation(option);
                    }
                }
                return lib.CallNextHookEx(hhk, nCode, wParam, info.pointer);
            }
        };
        hhk = lib.SetWindowsHookEx(User32.WH_MOUSE_LL, mouseHook, hMod, 0);
        int result;
        WinUser.MSG msg = new WinUser.MSG();
        while ((result = lib.GetMessage(msg, null, 0, 0)) != 0) {
            if (result == -1) {
                System.err.println("error in get message");
                break;
            } else {
                System.err.println("got message");
                lib.TranslateMessage(msg);
                lib.DispatchMessage(msg);
            }
        }
        lib.UnhookWindowsHookEx(hhk);
    }
}
