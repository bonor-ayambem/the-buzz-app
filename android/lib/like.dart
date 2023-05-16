// ignore_for_file: file_names
class Like {
  final int id;
  final String title;
  final int likes;

  Like(this.id, this.title, this.likes);

  Like.fromJson(Map<String, dynamic> json)
      : title = json['mTitle'],
        id = json['mId'],
        likes = json['mLikes'];

  Map<String, dynamic> toJson() => {
        'mTitle': title,
        'mId': id,
        'mLikes': likes,
      };
}
