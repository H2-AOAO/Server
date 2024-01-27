package kr.sesac.aoao.server.item.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.sesac.aoao.server.user.repository.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserItemEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@ManyToOne
	@JoinColumn(name = "item_id")
	private ItemEntity item;

	@Column
	private int item_num;

	public UserItemEntity(UserEntity userEntity, ItemEntity itemEntity, int item_num) {
		this.user = userEntity;
		this.item = itemEntity;
		this.item_num = item_num;
	}
	public void changeItemNum(int num) {
		this.item_num = num;
	}
	public void changeItem(ItemEntity item){this.item = item;}

}
