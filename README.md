# cloudmusic_api
接口列表

用户相关接口 /User
 
歌单相关 /SongListInfo

用户操作相关（点赞，收藏，关注） /Operation/cancelCollectList

单曲相关 /SongInfo

专辑相关 /AlbumInfo

分享动态相关 /share

动态附带图片 /shareImg

歌手相关 /singer

歌单列表相关 /SongList

标签相关 /tag


#总结
作者真正意义上第一次独立完成的后端开发项目，主要是使用springMVC和springBoot框架完成。针对从开始的数据库设计到最后的接口设计阶段进行一些总结：   
1.首先就是数据库表建立时，命名不够规范，导致后面与数据库链接时，变量大小不同导致出错。   
2.前期需求分析，功能流图，结构图，e-r图等设计不够完善，导致相关接口兼容性不够好。