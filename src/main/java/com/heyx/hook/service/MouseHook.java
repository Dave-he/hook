package com.heyx.hook.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.jna.Structure;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

public class MouseHook implements Runnable {

    public static final int WM_MOUSEMOVE = 512;
    private static WinUser.HHOOK hhk;
    private static LowLevelMouseProc mouseHook;
    final static User32 lib = User32.INSTANCE;
    private boolean [] on_off=null;

    public MouseHook(boolean [] on_off){
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
                SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String fileName=df1.format(new Date());
                String time=df2.format(new Date());
                BufferedWriter bw1=null;
                BufferedWriter bw2=null;
                try {
                    bw1=new BufferedWriter(new FileWriter(new File(".//log//"+fileName+"_Mouse.txt"),true));
                    bw2=new BufferedWriter(new FileWriter(new File(".//log//"+fileName+"_Common.txt"),true));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!on_off[0]) {
                    System.exit(0);
                }
                if (nCode >= 0) {
                    if (wParam.intValue() == MouseHook.WM_MOUSEMOVE) {
                        try {
                            bw1.write(time + "  ####  " + "x=" + info.pt.x
                                    + " y=" + info.pt.y + "\r\n");
                            bw2.write(time + "  ####  " + "x=" + info.pt.x
                                    + " y=" + info.pt.y + "\r\n");
                            bw1.flush();
                            bw2.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
