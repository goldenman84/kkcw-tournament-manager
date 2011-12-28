SET(PROJECT_SOURCES
    ../src/controller/controller.cpp
    ../src/model/tournament.cpp
    ../src/model/category.cpp
)

ADD_EXECUTABLE(controller.wt
  ${PROJECT_SOURCES}
)

TARGET_LINK_LIBRARIES(controller.wt wthttp wt wtdbomysql /usr/local/lib/libwtdbomysql.so.23  ${SSL_LIBRARIES} ${BOOST_WT_LIBRARIES} ${BOOST_WTHTTP_LIBRARIES} ${ZLIB_LIBRARIES} ${ASIO_LIBRARIES})

INCLUDE_DIRECTORIES(${WT_SOURCE_DIR}/src /usr/local/include/Wt/Dbo/backend/)

ADD_DEPENDENCIES(controller.wt wt ${EXAMPLES_CONNECTOR})

SET(EXECUTABLE_OUTPUT_PATH ../target)


