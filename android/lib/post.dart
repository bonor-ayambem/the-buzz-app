// ignore_for_file: file_names
class Post {
  final int id;
  int likes;
  final String title;
  final String message;

  Post(this.id, this.likes, this.title, this.message);

  Post.fromJson(Map<String, dynamic> json)
      : id = json['mId'],
        title = json['mTitle'],
        message = json['mContent'],
        likes = json['mLikes'];

  Map<String, dynamic> toJson() => {
        'mId': id,
        'mlikes': likes,
        'mTitle': title,
        'mContent': message,
      };
}
