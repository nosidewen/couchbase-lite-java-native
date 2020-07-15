TARGET ?= libcbljavasqlitecustom.so.1.0.0
SRC_DIRS ?= .

SRCS := $(shell find $(SRC_DIRS) -name '*.cpp' -or -name '*.c' -or -name '*.s')
OBJS := $(addsuffix .o,$(basename $(SRCS)))
DEPS := $(OBJS:.o=.d)

INC_DIRS := $(shell find $(SRC_DIRS) -type d)
INC_FLAGS := $(addprefix -I,$(INC_DIRS))

CPPFLAGS ?= $(INC_FLAGS) -MMD -MP
CPPFLAGS += -fPIC
CPPFLAGS += -DU_STATIC_IMPLEMENTATION
CPPFLAGS += -ffunction-sections
CPPFLAGS += -fdata-sections
CPPFLAGS += -fomit-frame-pointer

LDLIBS += -Wl,--no-undefined
LDFLAGS += -lpthread
LDFLAGS += -ldl
LDLIBS += -lsqlite3
LDFLAGS += -shared
LDFLAGS += -Wl,-soname,libcbljavasqlitecustom.so

$(TARGET): $(OBJS)
	$(CXX) $(LDFLAGS) $(OBJS) -o $@ $(LOADLIBES) $(LDLIBS)

.PHONY: clean
clean:
	$(RM) $(TARGET) $(OBJS) $(DEPS)

-include $(DEPS)
