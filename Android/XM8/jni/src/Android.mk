LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := main

SDL_PATH := ../SDL

LOCAL_C_INCLUDES := $(LOCAL_PATH)/$(SDL_PATH)/include \
	$(LOCAL_PATH)/../../../../UI \
	$(LOCAL_PATH)/../../../../ePC-8801MA \
	$(LOCAL_PATH)/../../../../ePC-8801MA/vm \
	$(LOCAL_PATH)/../../../../ePC-8801MA/vm/pc8801 \
	$(LOCAL_PATH)/../../../../ePC-8801MA/vm/fmgen

LOCAL_CFLAGS += -DSDL -D_PC8801MA -fexceptions

LOCAL_SRC_FILES := $(SDL_PATH)/src/main/android/SDL_android_main.c \
	intent.c \
	../../../../UI/app.cpp \
	../../../../UI/audio.cpp \
	../../../../UI/converter.cpp \
	../../../../UI/diskmgr.cpp \
	../../../../UI/emu.cpp \
	../../../../UI/emu_sdl.cpp \
	../../../../UI/file.cpp \
	../../../../UI/fileio.cpp \
	../../../../UI/font.cpp \
	../../../../UI/input.cpp \
	../../../../UI/main.cpp \
	../../../../UI/menu.cpp \
	../../../../UI/menulist.cpp \
	../../../../UI/menuitem.cpp \
	../../../../UI/platform.cpp \
	../../../../UI/setting.cpp \
	../../../../UI/softkey.cpp \
	../../../../UI/tapemgr.cpp \
	../../../../UI/video.cpp \
	../../../../ePC-8801MA/common.cpp \
	../../../../ePC-8801MA/config.cpp \
	../../../../ePC-8801MA/fifo.cpp \
	../../../../ePC-8801MA/vm/disk.cpp \
	../../../../ePC-8801MA/vm/event.cpp \
	../../../../ePC-8801MA/vm/i8251.cpp \
	../../../../ePC-8801MA/vm/i8253.cpp \
	../../../../ePC-8801MA/vm/i8255.cpp \
	../../../../ePC-8801MA/vm/disksub.cpp \
	../../../../ePC-8801MA/vm/pcm1bit.cpp \
	../../../../ePC-8801MA/vm/upd765a.cpp \
	../../../../ePC-8801MA/vm/upd1990a.cpp \
	../../../../ePC-8801MA/vm/fmsound.cpp \
	../../../../ePC-8801MA/vm/z80.cpp \
	../../../../ePC-8801MA/vm/fmgen/opna.cpp \
	../../../../ePC-8801MA/vm/fmgen/psg.cpp \
	../../../../ePC-8801MA/vm/fmgen/fmgen.cpp \
	../../../../ePC-8801MA/vm/fmgen/fmtimer.cpp \
	../../../../ePC-8801MA/vm/pc8801/pc88.cpp \
	../../../../ePC-8801MA/vm/pc8801/pc8801.cpp

LOCAL_SHARED_LIBRARIES := SDL2

LOCAL_LDLIBS := -lGLESv1_CM -lGLESv2 -llog

include $(BUILD_SHARED_LIBRARY)
